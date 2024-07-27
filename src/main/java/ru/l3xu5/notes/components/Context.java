package ru.l3xu5.notes.components;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Context {
    SendMessage processUpdate(Update update);

    default SendMessage buildMessage(String text, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

    default Context getNextContext() {
        return this;
    }
}
