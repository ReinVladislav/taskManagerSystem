package ru.rein.taskManagementSystem.Models.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus {
    PENDING("В ожидании"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершено");

    private final String displayName;
    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static TaskStatus fromDisplayName(String displayName) throws IllegalArgumentException{
        for (TaskStatus status : values()) {
            if (status.displayName.equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неизвестный статус: " + displayName);
    }
}
