package ru.rein.taskManagementSystem.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.rein.taskManagementSystem.DTO.ResponseTaskDto;
import ru.rein.taskManagementSystem.Models.Enums.TaskPriority;
import ru.rein.taskManagementSystem.Models.Enums.TaskStatus;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Models.Task;
import ru.rein.taskManagementSystem.Repositories.TaskRepository;
import ru.rein.taskManagementSystem.Services.PersonService;
import ru.rein.taskManagementSystem.Services.TaskService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PersonService personService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTasksByAuthor_asAdmin_success() {
        String mail = "admin@example.com";
        Person person = new Person();
        person.setMail(mail);
        person.setRole("ROLE_ADMIN");

        Task task = new Task(1L, "a", "s", TaskStatus.IN_PROGRESS, TaskPriority.HIGH
                , new ArrayList<>(), new Person(), new Person());
        Page<Task> tasksPage = new PageImpl<>(Collections.singletonList(task));

        Mockito.when(personService.getPersonByMail(mail)).thenReturn(person);
        Mockito.when(taskRepository.findByAuthor(person, PageRequest.of(0, 5))).thenReturn(tasksPage);

        Page<ResponseTaskDto> result = taskService.getTasksByAuthor(mail, 0);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().size());
    }

    @Test
    void testGetTasksByAuthor_notAdmin_throwsException() {
        String mail = "user@example.com";
        Person user = new Person();
        user.setMail(mail);
        user.setRole("ROLE_USER");

        Mockito.when(personService.getPersonByMail(mail)).thenReturn(user);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                taskService.getTasksByAuthor(mail, 0));

        Assertions.assertEquals("Пользователь user@example.com не является администратором", exception.getMessage());
    }

    @Test
    void testGetTasksByImplementer_success() {
        String mail = "user@example.com";
        Person implementer = new Person();
        implementer.setMail(mail);

        Task task = new Task(1L, "a", "s", TaskStatus.IN_PROGRESS, TaskPriority.HIGH
                , new ArrayList<>(), new Person(), new Person());
        Page<Task> tasksPage = new PageImpl<>(Collections.singletonList(task));

        Mockito.when(personService.getPersonByMail(mail)).thenReturn(implementer);
        Mockito.when(taskRepository.findByImplementer(implementer, PageRequest.of(0, 5))).thenReturn(tasksPage);

        Page<ResponseTaskDto> result = taskService.getTasksByImplementer(mail, 0);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().size());

    }

    @Test
    void testGetTaskById_success() {
        long taskId = 1L;
        Task task = new Task(1L, "a", "s", TaskStatus.IN_PROGRESS, TaskPriority.HIGH
                , new ArrayList<>(), new Person(), new Person());

        task.setId(taskId);

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(taskId, result.getId());
    }

    @Test
    void testGetTaskById_notFound_throwsException() {
        long taskId = 1L;

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                taskService.getTaskById(taskId));

        Assertions.assertEquals("Задачи с id=1 не существует", exception.getMessage());
    }
    @Test
    void testShowAllTask_success() {
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 5);

        Task task = new Task(1L, "a", "s", TaskStatus.IN_PROGRESS, TaskPriority.HIGH
                , new ArrayList<>(), new Person(), new Person());
        Page<Task> tasksPage = new PageImpl<>(Collections.singletonList(task));

        Mockito.when(taskRepository.findAll(pageable)).thenReturn(tasksPage);

        Page<ResponseTaskDto> result = taskService.showAllTask(pageNumber);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().size());

        ResponseTaskDto dto = result.getContent().get(0);
        Assertions.assertEquals(task.getId(), dto.getId());
    }

    @Test
    void testShowAllTask_emptyPage() {
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 5);

        Page<Task> emptyTasksPage = new PageImpl<>(Collections.emptyList());

        Mockito.when(taskRepository.findAll(pageable)).thenReturn(emptyTasksPage);

        Page<ResponseTaskDto> result = taskService.showAllTask(pageNumber);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getContent().isEmpty());
    }
}

