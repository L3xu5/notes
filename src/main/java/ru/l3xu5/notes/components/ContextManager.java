package ru.l3xu5.notes.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ContextManager {
    private Context context;

    @Autowired
    ContextManager(Context context) {
        this.context = context;
    }

    public SendMessage processUpdate(Update update) {
        SendMessage message = context.processUpdate(update);
        context = context.getNextContext();
        return message;
    }
}
