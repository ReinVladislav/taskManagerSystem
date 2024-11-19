package ru.rein.taskManagementSystem.security;

import lombok.RequiredArgsConstructor;
import ru.rein.taskManagementSystem.Services.PersonService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class CustomPersonDetailService implements UserDetailsService {
    private final PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        var person = personService.getPersonByMail(mail);
        return PersonPrincipal.builder()
                .personId(person.getId())
                .mail(person.getMail())
                .password(person.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(person.getRole())))
                .build();
    }
}
