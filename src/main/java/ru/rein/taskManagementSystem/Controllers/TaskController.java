package ru.rein.taskManagementSystem.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.rein.taskManagementSystem.DTO.ResponseDto;
import ru.rein.taskManagementSystem.DTO.ResponseTaskDto;
import ru.rein.taskManagementSystem.Services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@Tag(name = "Task API", description = "API для просмотра задач")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/byAuthor")
    @Operation(summary = "Получить задачи по автору",
            description = "Получить все задачи по почте автора, который их создал. Есть пагинация. На стренице 5 задач, можно указать номер страницы(по уполчанию 0)",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
    public ResponseDto<Page<ResponseTaskDto>> getTasksByAuthorMail(@RequestParam("mail") String mail
            , @RequestParam(value = "pageNumber", required = false,defaultValue = "0") Integer pageNumber){
        return new ResponseDto<>(taskService.getTasksByAuthor(mail, pageNumber));
    }

    @GetMapping("/byImplementer")
    @Operation(summary = "Получить задачи по исполнителю",
            description = "Получить все задачи по почте пользователя, который выполняет эти задачи. Есть пагинация. На стренице 5 задач, можно указать номер страницы(по уполчанию 0)"
            ,responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
    public ResponseDto<Page<ResponseTaskDto>> getTasksByImplementerMail(@RequestParam("mail") String mail
            , @RequestParam(value = "pageNumber", required = false,defaultValue = "0") Integer pageNumber){
        return new ResponseDto<>(taskService.getTasksByImplementer(mail,pageNumber));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseDto<Object> handleRuntimeException(RuntimeException ex) {
        return new ResponseDto<Object>(null, ex.getMessage());
    }
}
