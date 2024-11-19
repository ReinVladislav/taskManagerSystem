package ru.rein.taskManagementSystem.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rein.taskManagementSystem.Models.Person;

import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByMail(String mail);

    boolean existsByMail(String mail);
}
