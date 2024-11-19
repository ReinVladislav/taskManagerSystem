package ru.rein.taskManagementSystem.Services;

import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.rein.taskManagementSystem.DTO.LoginResponse;
import ru.rein.taskManagementSystem.security.JwtIssuer;
import ru.rein.taskManagementSystem.security.PersonPrincipal;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;


    public LoginResponse attemptLogin(String mail, String password) throws BadCredentialsException {
        try{
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(mail, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            PersonPrincipal principal = (PersonPrincipal) authentication.getPrincipal();

            String token = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .personId(principal.getPersonId())
                    .mail(principal.getMail())
                    .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .build());

            return LoginResponse.builder()
                    .token(token)
                    .build();
        }catch (Exception e){
            throw new BadCredentialsException("Неверный логин или пароль");
        }

    }
}