package ru.l3xu5.notes.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface GeneralBotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Register"),
            new BotCommand("/add", "Add new note"),
            new BotCommand("/remove", "Remove note"),
            new BotCommand("/list", "Print list of all notes"),
            new BotCommand("/help", "help menu")
    );

    String HELP_TEXT = """
            This bot will help to keep and manage your notes. \
            The following commands are available to you:
            
            /start - Register
            /add - Add new note
            /remove - Remove note
            /list - Print list of all notes
            /help - help menu""";
}
