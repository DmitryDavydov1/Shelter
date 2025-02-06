package com.example.bot._for_shelter.command.trialPeriod;

import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageService;
import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.service.AdoptionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ExtensionTrialPeriod implements Command {


    private final SendBotMessageService sendBotMessageService;
    private final AdoptionService adoptionService;

    private final String text = """
            Нашей командой принято решение о продление тестового периода усыновления на
            """;

    public ExtensionTrialPeriod(SendBotMessageService sendBotMessageService, AdoptionService adoptionService) {
        this.sendBotMessageService = sendBotMessageService;
        this.adoptionService = adoptionService;
    }

    @Override
    public void execute(Update update) {
        String callbackText = update.getCallbackQuery().getData();
        String[] parts = callbackText.split("-");
        increasingPeriod(Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));

    }

    @Override
    public boolean isSupport(String command) {
        try {
            String[] parts = command.split("-");
        } catch (Exception e) {
            return false;
        }
        String[] parts = command.split("-");
        return parts[0].equals("продление");
    }


    public void increasingPeriod(Integer countDays, Integer adoptionId) {
        SendMessage sendMessage = new SendMessage();
        Adoption adoption = adoptionService.findByChatId(adoptionId);
        adoption.setLastDay(adoption.getCurrentDay() + countDays);
        adoptionService.saveAdoption(adoption);
        sendMessage.setChatId(adoption.getBotUser().getChatId());
        sendMessage.setText(text + countDays + " дней");
        sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
    }
}
