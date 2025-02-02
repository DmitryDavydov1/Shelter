package com.example.bot._for_shelter.command.forAdmin;

import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageService;
import com.example.bot._for_shelter.service.PhotoTgService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class SendWarningCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final PhotoTgService photoTgService;


    String text = """
            «Дорогой усыновитель, мы заметили, что ты
             заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее
             к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного»
            """;


    public SendWarningCommand(SendBotMessageService sendBotMessageService, PhotoTgService photoTgService) {
        this.sendBotMessageService = sendBotMessageService;
        this.photoTgService = photoTgService;
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(text);
        String buttonCallback = update.getCallbackQuery().getData();
        String[] parts = buttonCallback.split("-");
        sendMessage.setChatId(parts[2]);
        photoTgService.setsView(Long.valueOf(parts[3]));
        sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
    }


    @Override
    public boolean isSupport(String command) {
        try {
            String[] parts = command.split("-");
        } catch (Exception e) {
            return false;
        }
        String[] parts = command.split("-");
        return parts[0].equals("Плохой") && parts[1].equals("репорт");
    }
}

