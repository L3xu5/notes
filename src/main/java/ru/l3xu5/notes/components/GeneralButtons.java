package ru.l3xu5.notes.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class GeneralButtons {
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");
    private static final InlineKeyboardButton LIST_BUTTON = new InlineKeyboardButton("List");


    public static InlineKeyboardMarkup inlineMarkup() {
        HELP_BUTTON.setCallbackData("/help");
        LIST_BUTTON.setCallbackData("/list");

        List<InlineKeyboardButton> rowInlineGeneral = List.of(HELP_BUTTON);
        List<InlineKeyboardButton> rowInlineManage = List.of(LIST_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInlineManage, rowInlineGeneral);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
