package dev.haskin.cookrecipes.controller.auth;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.haskin.cookrecipes.dto.ApiResponse;
import dev.haskin.cookrecipes.dto.auth.JwtAuthenticationResponse;
import dev.haskin.cookrecipes.dto.auth.LoginRequest;
import dev.haskin.cookrecipes.dto.auth.SignUpRequest;
import dev.haskin.cookrecipes.dto.auth.UserDTO;
import dev.haskin.cookrecipes.security.JwtTokenProvider;
import dev.haskin.cookrecipes.security.UserPrincipal;
import dev.haskin.cookrecipes.service.auth.AuthService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserDTO result = this.authService.signUp(signUpRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id, Authentication authentication) {
        this.authService.deleteById(id, (UserPrincipal) authentication.getPrincipal());
    }
}
