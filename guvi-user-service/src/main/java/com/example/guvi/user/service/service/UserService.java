package com.example.guvi.user.service.service;

import com.example.guvi.user.service.dto.request.UserRequestDto;
import com.example.guvi.user.service.dto.response.LoginResponseDto;
import com.example.guvi.user.service.dto.response.UserResponseDto;
import com.example.guvi.user.service.exception.InvalidCredentialsException;
import com.example.guvi.user.service.exception.ResourceNotFoundException;
import com.example.guvi.user.service.exception.UserExistException;
import com.example.guvi.user.service.model.User;
import com.example.guvi.user.service.repository.UserRepository;
import com.example.guvi.user.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {

        userRepository.findByUsername(userRequestDto.getUsername())
                .ifPresent(user -> {
                    throw new UserExistException("Username already exists");
                });

        User user = userRepository.save(mapToUser(userRequestDto));
        return mapToDto(user);
    }

    public LoginResponseDto login(UserRequestDto requestDto) {

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isValid = passwordEncoder.matches(requestDto.getPassword(), user.getPassword());

        if (!isValid) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return mapToLoginDto(user);
    }

    private LoginResponseDto mapToLoginDto(User user) {

        return LoginResponseDto.builder()
                .token(jwtService.generateToken(user.getUsername()))
                .build();
    }
    private UserResponseDto mapToDto(User user) {

        return UserResponseDto.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    private User mapToUser(UserRequestDto requestDto){

        return User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role("CUSTOMER")
                .build();
    }


}
