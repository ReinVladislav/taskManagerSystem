package ru.rein.taskManagementSystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Dto для получения токена")
public class LoginResponse {
    @Schema(name = "Токен")
    private String token = null;
    @Schema(name = "Сообщение")
    private String Message = null;
}
