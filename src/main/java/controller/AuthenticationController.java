package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lullabe.springsecurity.entity.User;
import com.lullabe.springsecurity.repository.AuthencationResponse;
import com.lullabe.springsecurity.services.AuthenticationService;



@RestController
public class AuthenticationController {

	private final AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

	@PostMapping("/register")
	public ResponseEntity<AuthencationResponse> register(@RequestBody User request) {
		return ResponseEntity.ok(authenticationService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthencationResponse> login(@RequestBody User request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}

}
