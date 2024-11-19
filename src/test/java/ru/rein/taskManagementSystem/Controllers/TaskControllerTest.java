package ru.rein.taskManagementSystem.Controllers;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import ru.rein.taskManagementSystem.DTO.ResponseTaskDto;
import ru.rein.taskManagementSystem.Models.Enums.TaskPriority;
import ru.rein.taskManagementSystem.Models.Enums.TaskStatus;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Models.Task;
import ru.rein.taskManagementSystem.Services.TaskService;
import ru.rein.taskManagementSystem.security.WebSecurityConfig;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;


    @Test
    public void getTasksByAuthorMailOk() throws Exception {
        String mail = "user@mail.ru";
        Task task = new Task(1L,"","", TaskStatus.PENDING, TaskPriority.HIGH
                , new ArrayList<>(),new Person(),new Person());
        List<ResponseTaskDto> responseTaskDtos = Collections.singletonList(new ResponseTaskDto(task));
        Mockito.when(taskService.getTasksByAuthor(mail, 0))
                .thenReturn(new PageImpl<>(responseTaskDtos, PageRequest.of(0, 5), responseTaskDtos.size()));
        mockMvc.perform(get("/task/byAuthor").param("mail", mail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(1));
    }


    @Test
    public void getTasksByImplementerMailOk() throws Exception {
        String mail = "user@mail.ru";
        Task task = new Task(1L,"","", TaskStatus.PENDING, TaskPriority.HIGH
                , new ArrayList<>(),new Person(),new Person());
        List<ResponseTaskDto> responseTaskDtos = Collections.singletonList(new ResponseTaskDto(task));
        Mockito.when(taskService.getTasksByImplementer(mail, 0))
                .thenReturn(new PageImpl<>(responseTaskDtos, PageRequest.of(0, 5), responseTaskDtos.size()));
        mockMvc.perform(get("/task/byImplementer").param("mail", mail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(1));
    }


    @Test
    public void getTasksByAuthorMailException() throws Exception {

        String mail = "user@mail.ru";
        Mockito.when(taskService.getTasksByAuthor(mail, 0))
                .thenThrow(new IllegalArgumentException("Пользователь "+mail+" не является администратором"));
        mockMvc.perform(get("/task/byAuthor").param("mail", mail))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.message").value("Пользователь "+mail+" не является администратором"));
    }
    @Test
    public void getTasksByImplementerMailException() throws Exception {

        String mail = "user@mail.ru";
        Mockito.when(taskService.getTasksByImplementer(mail, 0))
                .thenThrow(new IllegalArgumentException("Пользовател с почтой "+mail+" не существует"));
        mockMvc.perform(get("/task/byImplementer").param("mail", mail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Пользовател с почтой "+mail+" не существует"));
    }
}


