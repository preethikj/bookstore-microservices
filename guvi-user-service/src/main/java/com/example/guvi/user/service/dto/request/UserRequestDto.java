package com.example.guvi.user.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserRequestDto {

    @NotNull(message = "Username can't be empty")
    private String username;
    @NotNull(message = "Password can't be empty")
    private String password;
    private String role;

}
