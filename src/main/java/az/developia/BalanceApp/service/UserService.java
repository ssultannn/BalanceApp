package az.developia.BalanceApp.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import az.developia.BalanceApp.entity.UserEntity;
import az.developia.BalanceApp.repository.UserRepository;
import az.developia.BalanceApp.request.UserLoginRequest;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> register(UserEntity user) {
        // Проверяем уникальность имени пользователя
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(409).body("Username already exists!");
        }

        // Проверяем уникальность почты
        if (userRepository.findByMail(user.getMail()) != null) {
            return ResponseEntity.status(409).body("Email already exists!");
        }

        // Сохраняем нового пользователя
        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setMail(user.getMail());
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }

    public ResponseEntity<String> login(UserLoginRequest user) {
        UserEntity foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.status(401).body("Invalid credentials!");
    }
}
