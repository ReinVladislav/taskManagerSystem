package ru.rein.taskManagementSystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(description = "Dto для добавления комментария к задачи")
public class RequestAddCommentDto {

    @NotBlank(message = "Поле taskId не должно быть пустым")
    @Schema( example = "1")
    private long taskId;
    @NotBlank(message = "Поле commentText не должно быть пустым")
    @Schema( example = "Задача будет выполнена к среде")
    private String commentText;
}
