package ru.rein.taskManagementSystem.DTO;

import lombok.Data;
import ru.rein.taskManagementSystem.Models.Comment;

@Data
public class CommentDTO {

    private String text;
    private String personMail;

    public CommentDTO(Comment comment) {
        text = comment.getText();
        personMail = comment.getPerson().getMail();
    }
}
