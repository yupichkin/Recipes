package recipes.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import recipes.security.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public interface UserRepository extends CrudRepository<User, String> {
    User findUserByEmail(String email);
    //void save(User user) provided by extended class
}
