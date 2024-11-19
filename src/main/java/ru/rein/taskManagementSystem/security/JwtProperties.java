package ru.rein.taskManagementSystem.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("application.properties")
@AllArgsConstructor
@NoArgsConstructor
public class JwtProperties {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token-duration}")
    private Duration tokenDuration;

}
