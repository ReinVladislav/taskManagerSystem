package ru.rein.taskManagementSystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.rein.taskManagementSystem.Models.Task;

import java.util.List;

@Data
@Schema(description = "Dto для задач")
public class ResponseTaskDto {

    private long id;
    private String header;
    private String description;
    private String status;
    private String priority;
    private List<CommentDTO> comments;
    private String authorMail;
    private String implementerMail;

    public ResponseTaskDto(Task task) {
        id = task.getId();
        header = task.getHeader();
        description = task.getDescription();
        status = task.getStatus().getDisplayName();
        priority = task.getPriority().getDisplayName();
        comments = task.getComments().stream().map(CommentDTO::new).toList();
        authorMail = task.getAuthor().getMail();
        implementerMail = task.getImplementer().getMail();
    }
}
