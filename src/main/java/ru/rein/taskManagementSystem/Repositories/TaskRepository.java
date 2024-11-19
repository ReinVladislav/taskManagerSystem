package ru.rein.taskManagementSystem.Repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Models.Task;


@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Page<Task> findByAuthor(Person author, Pageable pageable);

    Page<Task> findByImplementer(Person implementer, Pageable pageable);

    Page<Task> findAll(Pageable pageable);

}
