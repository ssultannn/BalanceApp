package az.developia.BalanceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import az.developia.BalanceApp.entity.BalanceEntity;
import az.developia.BalanceApp.entity.UserEntity;
import az.developia.BalanceApp.repository.UserRepository;
import az.developia.BalanceApp.request.UserLoginRequest;
import az.developia.BalanceApp.response.BalanceResponse;
import az.developia.BalanceApp.service.BalanceService;
import az.developia.BalanceApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BalanceService balanceService;


	@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest user) {
        return userService.login(user);
    }
	    @PostMapping("/{userId}/balance")
	    public ResponseEntity<BalanceResponse> calculateAndSaveBalance(@PathVariable Long userId) {
	        BalanceResponse response = balanceService.calculateAndSaveBalance(userId);
	        return ResponseEntity.ok(response);
	    }

	    @GetMapping("/{userId}/balance")
	    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long userId) {
	        BalanceResponse response = balanceService.getBalanceByUserId(userId);
	        return ResponseEntity.ok(response);
	    }
}
