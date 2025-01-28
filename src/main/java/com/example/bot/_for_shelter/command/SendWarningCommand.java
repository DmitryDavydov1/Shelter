package com.example.bot._for_shelter.command;

import org.aspectj.bridge.ICommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class SendWarningCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    String text = """
            «Дорогой усыновитель, мы заметили, что ты
             заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее
             к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного»
            """;

    public SendWarningCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(text);


        String buttonCallback = update.getCallbackQuery().getData();
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(buttonCallback);
//        sendMessage.setChatId(matcher.group());
        if (matcher.find()) {
            String group2 = matcher.group(2);
            sendMessage.setChatId(group2);
        }

        sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);

    }

    @Override
    public boolean isSupport(String command) {
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String group1 = matcher.group(1);
            return group1.equals("Плохой-репорт-");
        } else {
            // Логируем или обрабатываем случай, когда совпадение не найдено
            System.out.println("No match found for command: " + command);
        }

        return false;
    }
}
