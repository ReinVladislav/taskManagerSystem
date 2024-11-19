package ru.rein.taskManagementSystem.Services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.rein.taskManagementSystem.DTO.RequestTaskDto;
import ru.rein.taskManagementSystem.DTO.ResponseTaskDto;
import ru.rein.taskManagementSystem.Models.Enums.TaskStatus;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Models.Task;
import ru.rein.taskManagementSystem.Repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final PersonService personService;

    public Page<ResponseTaskDto> getTasksByAuthor(String mail, int pageNumber){
        Person person = personService.getPersonByMail(mail);
        if(person.getRole().equals("ROLE_ADMIN")){
            Pageable pageable = PageRequest.of(pageNumber,5);
            return taskRepository.findByAuthor(person, pageable).map(ResponseTaskDto::new);
        }
        throw new IllegalArgumentException("Пользователь "+mail+" не является администратором");
    }

    public Page<ResponseTaskDto> getTasksByImplementer(String mail, int pageNumber){
        Person person = personService.getPersonByMail(mail);
        Pageable pageable = PageRequest.of(pageNumber,5);
        return taskRepository.findByImplementer(person, pageable).map(ResponseTaskDto::new);
    }

    public void changeStatus(long taskId, TaskStatus newTaskStatus, String mail){
        Task task = getTaskById(taskId);
        Person person = personService.getPersonByMail(mail);
        if(task.getImplementer().getMail().equals(mail) || person.getRole().equals("ROLE_ADMIN")){
            task.setStatus(newTaskStatus);
            taskRepository.save(task);
        }else {
            throw new IllegalArgumentException("Пользователь "+mail+" не является исполнителем задачи с id="+taskId);
        }
    }

    public Task getTaskById(long id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            return task.get();
        }
        throw new IllegalArgumentException("Задачи с id="+id+" не существует");
    }

    public void createTask(RequestTaskDto responseTaskDto, String authorMail){
        Person author = personService.getPersonByMail(authorMail);
        if(responseTaskDto.getImplementerMail()==null){
            throw new IllegalArgumentException("При создании задачи, все поля должны быть заполнены");
        }
        Person implementer = personService.getPersonByMail(responseTaskDto.getImplementerMail());
        Task newTask = new Task(responseTaskDto, author, implementer);
        taskRepository.save(newTask);
    }
    public void updateTask(RequestTaskDto responseTaskDto){
        if(responseTaskDto.getId() == null){
            throw new IllegalArgumentException("Поле id не должно быть пустым");
        }
        Task updateTask = getTaskById(responseTaskDto.getId());
        if(responseTaskDto.getHeader() != null){
            updateTask.setHeader(responseTaskDto.getHeader());
        }
        if(responseTaskDto.getDescription() != null){
            updateTask.setDescription(responseTaskDto.getDescription());
        }
        if(responseTaskDto.getPriority() != null){
            updateTask.setPriority(responseTaskDto.getPriority());
        }
        if(responseTaskDto.getStatus() != null){
            updateTask.setStatus(responseTaskDto.getStatus());
        }
        if(responseTaskDto.getId() != null){
            Person implementer = personService.getPersonByMail(responseTaskDto.getImplementerMail());
            updateTask.setImplementer(implementer);
        }

        taskRepository.save(updateTask);
    }

    public Page<ResponseTaskDto> showAllTask(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 5);
        return taskRepository.findAll(pageable).map(ResponseTaskDto::new);
    }

    public void deleteTask(long taskId){
        taskRepository.delete(getTaskById(taskId));
    }

}
