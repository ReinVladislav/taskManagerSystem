package ru.rein.taskManagementSystem.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rein.taskManagementSystem.Models.Comment;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Models.Task;
import ru.rein.taskManagementSystem.Repositories.CommentRepository;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final PersonService personService;

    public void addComment(long taskId, String taskComment, String mail){
        Task task = taskService.getTaskById(taskId);
        Person person = personService.getPersonByMail(mail);
        Comment newComment = new Comment(taskComment, person, task);
        commentRepository.save(newComment);
    }
}