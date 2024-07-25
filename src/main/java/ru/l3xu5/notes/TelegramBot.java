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
import ru.l3xu5.notes.components.BotCommands;
import ru.l3xu5.notes.components.Buttons;
import ru.l3xu5.notes.config.BotConfig;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot implements BotCommands {
    final BotConfig config;
    final UserRepository users;

    public TelegramBot(@NonNull BotConfig config, @NonNull UserRepository users) {
        super(config.getToken());
        this.config = config;
        this.users = users;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(@NonNull Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processMessage(update.getMessage().getText(), update.getMessage().getChatId());
        } else if (update.hasCallbackQuery()) {
            processMessage(update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage().getChatId());
        }
    }

    private void processMessage(String text, long chatId) {
        switch (text) {
            case "/start":
                startBot(chatId);
                break;
            case "/help":
                sendHelp(chatId);
                break;
            default:
                log.info("Unknown message: {}", text);
        }
    }

    private void sendHelp(long chatId) {
        final SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(HELP_TEXT);
        try {
            execute(message);
            log.info("Help message was sent.");
        } catch (TelegramApiException e) {
            log.error("{} while replying to /help", e.getMessage());
        }
    }

    private void startBot(long chatId) {
        final SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(Buttons.inlineMarkup());
        if (users.existsById(chatId)) {
            message.setText("Hello!\nYou are already registered.");
            log.info("Existed user used /start");
        } else {
            message.setText("Hello!\nWelcome to Notes.");
            log.info("New user used /start");
            users.save(new User(chatId));
        }
        try {
            execute(message);
            log.info("/start message was sent");
        } catch (TelegramApiException e) {
            log.error("{} while replying to /start", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }
}
