package ru.rein.taskManagementSystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Dto для регистрации или авторизации")
public class LoginRequest {
    @Schema( example = "user1@mail.ru")
    private final String mail;
    @Schema( example = "111")
    private final String password;
}
