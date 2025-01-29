package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.repository.AdoptionRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ExtensionTrialPeriod implements Command {

    private final AdoptionRepository adoptionRepository;
    private final SendBotMessageService sendBotMessageService;

    private final String text = """
            Нашей командой принято решение о продление тестового периода усыновления на """;

    public ExtensionTrialPeriod(AdoptionRepository adoptionRepository, SendBotMessageService sendBotMessageService) {
        this.adoptionRepository = adoptionRepository;
        this.sendBotMessageService = sendBotMessageService;
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
        Adoption adoption = adoptionRepository.findById(adoptionId).orElse(null);
        adoption.setLastDay(adoption.getCurrentDay() + countDays);
        adoptionRepository.save(adoption);
        sendMessage.setChatId(adoption.getBotUser().getChatId());
        sendMessage.setText(text + countDays + " дней");
        sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
    }
}
