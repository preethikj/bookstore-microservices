package com.example.guvi.user.service.service;

import com.example.guvi.user.service.dto.request.UserRequestDto;
import com.example.guvi.user.service.dto.response.UserResponseDto;
import com.example.guvi.user.service.exception.UserExistException;
import com.example.guvi.user.service.model.User;
import com.example.guvi.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {

        userRepository.findByUsername(userRequestDto.getUsername())
                .ifPresent(user -> {
                    throw new UserExistException("Username already exists");
                });

        User user = userRepository.save(mapToUser(userRequestDto));
        return mapToDto(user);
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
                .password(requestDto.getPassword())
                .role("CUSTOMER")
                .build();
    }
}
