package recipes.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @Pattern(regexp = ".+@.+\\..+", message = "email is not correct")
    //not using @Email because there no checking for '.' in the end
    @NotNull
    @Column
    private String email;

    @NotBlank(message = "password is blank")
    @Size(min = 8, message = "to short password, required at least 8")
    @Column
    private String password;

    private String role; //as default will be ROLE_USER
}

