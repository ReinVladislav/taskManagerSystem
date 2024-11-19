package ru.rein.taskManagementSystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rein.taskManagementSystem.Models.Enums.TaskPriority;
import ru.rein.taskManagementSystem.Models.Enums.TaskStatus;
import ru.rein.taskManagementSystem.Models.Task;

@Data
@AllArgsConstructor
@Schema(description = "Dto для создания или редактирования задачи")
public class RequestTaskDto {
    @Schema(description = "Обезательное поле для редактирования задачи. При создании задачи не учитывается", example = "1")
    private Long id;
    @Schema( example = "Подготовить отчет")
    private String header;
    @Schema( example = "Подготовить месячный отчет по продажам")
    private String description;
    @Schema( example = "В ожидании")
    private TaskStatus status;
    @Schema( example = "Средний")
    private TaskPriority priority;
    @Schema( example = "user1@mail.ru")
    private String implementerMail;
}
