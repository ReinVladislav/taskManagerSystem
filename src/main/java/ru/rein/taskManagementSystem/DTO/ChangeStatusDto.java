package ru.rein.taskManagementSystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.rein.taskManagementSystem.Models.Enums.TaskStatus;

@Data
@Schema(description = "Dto для изменения статуса")
public class ChangeStatusDto {
    @NotBlank(message = "Поле taskId не должно быть пустым")
    @Schema( example = "1")
    private long taskId;
    @NotBlank(message = "Поле taskStatus не должно быть пустым")
    @Schema( example = "В процессе")
    private TaskStatus taskStatus;
}
