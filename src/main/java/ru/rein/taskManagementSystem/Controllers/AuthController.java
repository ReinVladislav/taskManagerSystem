package ru.rein.taskManagementSystem.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rein.taskManagementSystem.DTO.LoginRequest;
import ru.rein.taskManagementSystem.DTO.LoginResponse;
import ru.rein.taskManagementSystem.DTO.ResponseDto;
import ru.rein.taskManagementSystem.Exeptions.EmailAlreadyExistsException;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Services.AuthService;
import ru.rein.taskManagementSystem.Services.PersonService;

import java.io.IOException;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Auth API", description = "API для регистрации и авторизации")
public class AuthController {
    private final AuthService authService;
    private final PersonService personService;

    @PostMapping("/signin")
    @Operation(summary = "Авторизация",
            description = "Получение токена для зарегистрированного пользователя",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))))
    public LoginResponse signIn(@RequestBody @Validated LoginRequest request) {
        LoginResponse response = authService.attemptLogin(request.getMail(), request.getPassword());
        response.setMessage("Пользователь успешно прошёл аутентификацию");
        return response;

    }

    @PostMapping("/signup")
    @Operation(summary = "Регистрация",
            description = "Создаёт нового пользователся и отдаёт токен",
            responses = @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))))
    public LoginResponse signUp(@RequestBody @Validated LoginRequest request) {
        Person person = new Person(request.getMail(), request.getPassword());
        personService.savePerson(person);
        LoginResponse response = authService.attemptLogin(request.getMail(), request.getPassword());
        response.setMessage("Пользователь успешно зарегистрирован");
        return response;

    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseDto<Object> handleAuthenticationExceptionException(AuthenticationException ex) {
        return new ResponseDto<Object>(null, "Вы не авторизированны");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseDto<Object> handleRuntimeException(RuntimeException ex) {
        return new ResponseDto<Object>(null, ex.getMessage());
    }
}
