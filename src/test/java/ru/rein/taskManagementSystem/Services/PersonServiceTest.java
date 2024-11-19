package ru.rein.taskManagementSystem.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Repositories.PersonRepository;
import ru.rein.taskManagementSystem.Services.PersonService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetPersonByMail_success() {
        String mail = "user1@example.com";
        Person mockPerson = new Person();
        mockPerson.setMail(mail);

        Mockito.when(personRepository.findByMail(mail)).thenReturn(Optional.of(mockPerson));

        Person result = personService.getPersonByMail(mail);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mail, result.getMail());
        Mockito.verify(personRepository, Mockito.times(1)).findByMail(mail);
    }

    @Test
    void testGetPersonByMail_notFound() {
        String mail = "nonexistent@example.com";
        Mockito.when(personRepository.findByMail(mail)).thenReturn(Optional.empty());

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                personService.getPersonByMail(mail));

        Assertions.assertEquals("Пользовател с почтой " + mail + " не существует", exception.getMessage());
        Mockito.verify(personRepository, Mockito.times(1)).findByMail(mail);
    }
}
