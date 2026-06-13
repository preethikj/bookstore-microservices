package com.example.guvi.user.service.controller;

import com.example.guvi.user.service.dto.request.UserRequestDto;
import com.example.guvi.user.service.dto.response.LoginResponseDto;
import com.example.guvi.user.service.dto.response.UserResponseDto;
import com.example.guvi.user.service.security.JwtService;
import com.example.guvi.user.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto){
        UserResponseDto createdUser = userService.registerUser(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.login(userRequestDto));
    }

    //Just created for testing purpose
    /*@GetMapping("/me")
    public String me(Authentication authentication) {
        return authentication.getName();
    }*/

    //Only Admin have access to this endpoint
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "Welcome Admin";
    }
}
