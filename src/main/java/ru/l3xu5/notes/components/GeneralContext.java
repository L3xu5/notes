package ru.l3xu5.notes.components;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.l3xu5.notes.User;
import ru.l3xu5.notes.UserRepository;

import java.util.Date;
import java.util.Scanner;

import static ru.l3xu5.notes.components.DateParser.parse;

@Slf4j
@Data
@Component
@Primary
public class GeneralContext extends GeneralButtons implements Context, GeneralBotCommands {
    final UserRepository users;

    @Override
    public SendMessage processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return processMessage(update.getMessage().getText(), update.getMessage().getChatId());
        } else if (update.hasCallbackQuery()) {
            return processMessage(update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage().getChatId());
        }
        return null;
    }

    private SendMessage processMessage(String message, long chatId) throws NotEnoughDateArgumentException, InvalidDateFormatException {
        Scanner scanner = new Scanner(message);
        String command = scanner.next();
        try {
            return switch (command) {
                case "/start" -> startBot(chatId);
                case "/help" -> sendHelp(chatId);
                case "/list" -> sendList(chatId);
                case String s when s.startsWith("/add") -> addNote(scanner, chatId);
                case String s when s.startsWith("/remove") -> removeNote(scanner, chatId);
                default -> buildMessage(STR."Unknown command \{command}", chatId);
            };
        } catch (NotEnoughDateArgumentException | InvalidDateFormatException e) {
            return buildMessage(e.getMessage(), chatId);
        }
    }

    private SendMessage removeNote(Scanner scanner, long chatId) throws NotEnoughDateArgumentException, InvalidDateFormatException {
        Date dateTime = parse(scanner);
        users.removeNote(chatId, dateTime);
        log.info("Note was removed to user {}", chatId);
        return buildMessage(STR."Remove note at \{dateTime}", chatId);
    }

    private SendMessage addNote(Scanner scanner, long chatId) throws NotEnoughDateArgumentException, InvalidDateFormatException {
        Date dateTime = parse(scanner);
        scanner.useDelimiter("\\A");
        users.addNote(chatId, dateTime, scanner.hasNext() ? scanner.next() : "");
        log.info("New note was added to user {}", chatId);
        return buildMessage(STR."New note was added at \{dateTime}", chatId);
    }




    private SendMessage sendList(long chatId) {
        String notes = users.getNotes(chatId);
        return buildMessage(notes.isEmpty() ? "There are no notes yet." : notes, chatId);
    }

    private SendMessage sendHelp(long chatId) {
        return buildMessage(HELP_TEXT, chatId, true);
    }

    private SendMessage startBot(long chatId) {
        if (users.existsById(chatId)) {
            log.info("Existed user used /start");
            return buildMessage("Hello!\nYou are already registered.", chatId, true);
        }
        log.info("New user used /start");
        users.save(new User(chatId));
        return buildMessage("Hello!\nWelcome to Notes.", chatId, true);
    }

    public SendMessage buildMessage(String text, long chatId, boolean buttons) {
        SendMessage message = Context.super.buildMessage(text, chatId);
        if (buttons) {
            message.setReplyMarkup(inlineMarkup());
        }
        return message;
    }
}
