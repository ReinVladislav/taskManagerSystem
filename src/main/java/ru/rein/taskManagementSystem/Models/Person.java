package ru.rein.taskManagementSystem.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Email(message = "Некорректный формат электронной почты")
    @NotBlank(message = "Поле электронной почты не может быть пустым")
    @Length(max = 255, message = "Электронная почта не должна превышать 255 символов")
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> createdTask;

    @OneToMany(mappedBy = "implementer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasksToBeImplement;

    public Person(String mail, String password) {
        this.mail = mail;
        this.password = password;
        this.role = "ROLE_USER";
    }
}
