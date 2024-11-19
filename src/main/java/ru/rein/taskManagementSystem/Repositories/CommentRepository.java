package ru.rein.taskManagementSystem.Repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rein.taskManagementSystem.Models.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
