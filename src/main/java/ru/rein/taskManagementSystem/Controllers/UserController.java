package ru.rein.taskManagementSystem.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rein.taskManagementSystem.DTO.ChangeStatusDto;
import ru.rein.taskManagementSystem.DTO.RequestAddCommentDto;
import ru.rein.taskManagementSystem.DTO.ResponseDto;
import ru.rein.taskManagementSystem.Services.CommentService;
import ru.rein.taskManagementSystem.Services.TaskService;
import ru.rein.taskManagementSystem.security.PersonPrincipal;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "User API", description = "API для пользователся для взаимодействия с задачами")
public class UserController {
    private final TaskService taskService;
    private final CommentService commentService;

    @PatchMapping("/change_status")
    @Operation(summary = "Поменять статус задачи",
            description = "Принимает id задачи, статус которой нужно поменять и новый статус",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
    public ResponseDto<Object> changeStatus(@AuthenticationPrincipal PersonPrincipal personPrincipal,
                                            @RequestBody @Valid ChangeStatusDto data){
        taskService.changeStatus(data.getTaskId(), data.getTaskStatus(), personPrincipal.getMail());
        return new ResponseDto<Object>(null,"Статус задачи изменён на: "+data.getTaskStatus().getDisplayName());
    }

    @PostMapping("/add_comment")
    @Operation(summary = "Добавить комментарий",
            description = "Принимает id задачи, для которой добавляется комментарий и текст комментария",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))))
     public ResponseDto<Object> addComment(@AuthenticationPrincipal PersonPrincipal personPrincipal,
                                           @RequestBody() @Valid RequestAddCommentDto requestAddCommentDto){
        commentService.addComment(requestAddCommentDto.getTaskId(), requestAddCommentDto.getCommentText(), personPrincipal.getMail());
        return new ResponseDto<Object>(null,"Комментарий успешно добавлен");
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseDto<Object> handleRuntimeException(RuntimeException ex) {
        return new ResponseDto<Object>(null, ex.getMessage());

    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseDto<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");
        return new ResponseDto<>(null, errorMessage);
    }
}
