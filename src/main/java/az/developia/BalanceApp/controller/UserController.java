package az.developia.BalanceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import az.developia.BalanceApp.entity.UserEntity;
import az.developia.BalanceApp.exception.MyException;
import az.developia.BalanceApp.service.UserService;
import az.developia.BalanceApp.service.BalanceService;
import az.developia.BalanceApp.request.UserLoginRequest;
import az.developia.BalanceApp.response.BalanceResponse;
import az.developia.BalanceApp.security.UserContext;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private UserContext userContext;  

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new MyException("Username and password are required!");
        }
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new MyException("Username and password are required!");
        }
        return userService.login(user);
    }

    @PostMapping("/balance")
    public ResponseEntity<BalanceResponse> calculateAndSaveBalance() {
        Long userId = userContext.getCurrentUserId();  // Извлекаем userId из контекста
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }
        return ResponseEntity.ok(balanceService.calculateAndSaveBalance(userId));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        Long userId = userContext.getCurrentUserId();  // Извлекаем userId из контекста
        if (userId == null) {
            throw new MyException("User ID is required in context!");
        }
        return ResponseEntity.ok(balanceService.getBalanceByUserId(userId));
    }
}

