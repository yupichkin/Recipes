package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.user.UserRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
public class RegistrationController {
    @Value("${security.role.user.fullname}")
    String userRoleName;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        // input validation omitted for brevity
        System.out.println("USER EMAIL: " + user.getEmail());
        System.out.println("USER PASSWORD: " + user.getPassword());
        if(userRepo.existsById(user.getEmail())) {
            System.out.println("EMAIL ALREADY EXISTS");
            System.out.println(userRepo.existsById(user.getEmail()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this email already registered");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(userRoleName);
        userRepo.save(user);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
