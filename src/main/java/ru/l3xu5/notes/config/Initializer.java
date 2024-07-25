package ru.l3xu5.notes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.l3xu5.notes.TelegramBot;

@Slf4j
@Component
public class Initializer {
    final
    TelegramBot bot;

    public Initializer(TelegramBot bot) {
        this.bot = bot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void onContextRefreshed() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
