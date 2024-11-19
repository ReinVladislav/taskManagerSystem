package ru.rein.taskManagementSystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Dto для всех ответов. Содержит сообщение и данные(если метод что-то возвращает)")
public class ResponseDto<T> {
    @Schema(example = "data")
    private T data = null;
    @Schema(example = "ок")
    private String message = "ок";


    public ResponseDto(T data) {
        this.data = data;
    }

    public ResponseDto(String message) {
        this.message = message;
    }
}
