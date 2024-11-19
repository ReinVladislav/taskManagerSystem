package ru.rein.taskManagementSystem.Services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.rein.taskManagementSystem.Exeptions.EmailAlreadyExistsException;
import ru.rein.taskManagementSystem.Models.Person;
import ru.rein.taskManagementSystem.Repositories.PersonRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person getPersonByMail(String mail){
        Optional<Person> person = personRepository.findByMail(mail);
        if(person.isPresent()){
            return person.get();
        }else{
            throw new IllegalArgumentException("Пользовател с почтой "+mail+" не существует");
        }
    }

    public void savePerson(Person person){
        if(existByMail(person.getMail())){
            throw new EmailAlreadyExistsException("Пользователь с таким email уже существует");
        }
        person.setPassword(BCrypt.hashpw(person.getPassword(), BCrypt.gensalt(12)));
        personRepository.save(person);
    }

    public boolean existByMail(String mail){
        return personRepository.existsByMail(mail);
    }

}
