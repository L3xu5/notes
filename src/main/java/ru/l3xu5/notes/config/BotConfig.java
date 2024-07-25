package ru.l3xu5.notes.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Data
public class BotConfig {
    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;
}
