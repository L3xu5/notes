package ru.l3xu5.notes;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.l3xu5.notes.components.ContextManager;
import ru.l3xu5.notes.config.BotConfig;

import static ru.l3xu5.notes.components.GeneralBotCommands.LIST_OF_COMMANDS;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    final UserRepository users;
    final ContextManager contextManager;

    public TelegramBot(@NonNull BotConfig config, @NonNull UserRepository users, ContextManager contextManager) {
        super(config.getToken());
        this.config = config;
        this.users = users;
        this.contextManager = contextManager;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(@NonNull Update update) {
        SendMessage message = contextManager.processUpdate(update);
        try {
            execute(message);
            log.info("Message {} was sent to {}.", message.getText(), message.getChatId());
        } catch (TelegramApiException e) {
            log.error("{} while sending message {} to {}", e.getMessage(), message.getText(), message.getChatId());
        }
    }


    @Override
    public String getBotUsername() {
        return config.getName();
    }
}
