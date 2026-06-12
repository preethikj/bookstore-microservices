package com.example.guvi.user.service.controller;

import com.example.guvi.user.service.dto.request.UserRequestDto;
import com.example.guvi.user.service.dto.response.LoginResponseDto;
import com.example.guvi.user.service.dto.response.UserResponseDto;
import com.example.guvi.user.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public String home(){
        return "User Service Home Page";
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto){
        UserResponseDto createdUser = userService.registerUser(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser);
    }

    /*@PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody UserRequestDto requestDto) {

        return ResponseEntity.ok(userService.login(requestDto));
    }*/

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.login(userRequestDto));
    }
}
