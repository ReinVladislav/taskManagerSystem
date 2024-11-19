package ru.rein.taskManagementSystem.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.rein.taskManagementSystem.DTO.RequestTaskDto;
import ru.rein.taskManagementSystem.DTO.ResponseDto;
import ru.rein.taskManagementSystem.DTO.ResponseTaskDto;
import ru.rein.taskManagementSystem.Services.TaskService;
import ru.rein.taskManagementSystem.security.PersonPrincipal;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Tag(name = "Admin API", description = "API для админа для управления задачами")
public class AdminController {
    private final TaskService taskService;

    @PostMapping("task/create")
    @Operation(summary = "Создать новую задачу", description = "Создаёт новую задачу с указанными данными. Все данные обязательные, кроме id, оно не учитывается",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
    public ResponseDto<Object> createTask(@AuthenticationPrincipal PersonPrincipal personPrincipal,
                                          @RequestBody RequestTaskDto requestTaskDto){
        taskService.createTask(requestTaskDto, personPrincipal.getMail());
        return new ResponseDto<>(null, "Задача успешно созданна");
    }

    @PatchMapping("task/update")
    @Operation(summary = "Редактировать задачу",
            description = "Редактирует уже созданную задачу, если параметр не передаётся, то он остаётся преждним. Id редактируемой задачи передавать обязательно!",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
    public ResponseDto<Object> updateTask(@RequestBody RequestTaskDto requestTaskDto){
        taskService.updateTask(requestTaskDto);
        return new ResponseDto<>(null, "Задача успешно обновлена");
    }

    @GetMapping("/task/all")
    @Operation(summary = "Показать все задачи",
            description = "Показывает список всех задач. Есть пагинация. На стренице 5 задач, можно указать номер страницы(по уполчанию 0)",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
    public ResponseDto<Page<ResponseTaskDto>> showAll(
            @RequestParam(value = "pageNumber", required = false ,defaultValue = "0") Integer pageNumber){
        if (pageNumber ==null){
            pageNumber = 0;
        }
        return new ResponseDto<>(taskService.showAllTask(pageNumber),"ок");
    }

    @DeleteMapping("/task/delete")
    @Operation(summary = "Удалить задачу",
            description = "Удаляет уже созданную задачу по переданному id задачи",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
    public ResponseDto<Object> deleteTask(@RequestParam("taskId") long taskId){
        taskService.deleteTask(taskId);
        return new ResponseDto<>(null, "Задача с id="+taskId+" успешно удалена");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseDto<Object> handleRuntimeException(RuntimeException ex) {
        return new ResponseDto<>(null, ex.getMessage());
    }
}
