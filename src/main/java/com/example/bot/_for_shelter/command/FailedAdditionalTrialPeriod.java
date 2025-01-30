package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.repository.AdoptionRepository;
import com.example.bot._for_shelter.repository.PetRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class FailedAdditionalTrialPeriod implements Command {

    private final AdoptionRepository adoptionRepository;
    private final SendBotMessageService sendBotMessageService;
    private final PetRepository petRepository;

    public FailedAdditionalTrialPeriod(AdoptionRepository adoptionRepository, SendBotMessageService sendBotMessageService, PetRepository petRepository) {
        this.adoptionRepository = adoptionRepository;
        this.sendBotMessageService = sendBotMessageService;
        this.petRepository = petRepository;
    }


    String text = """
            К сожалению вы не прошли испытательный срок
            """;

    @Override
    public void execute(Update update) {
        String callbackText = update.getCallbackQuery().getData();
        String[] parts = callbackText.split("-");
        Adoption adoption = adoptionRepository.findById(Integer.parseInt(parts[3])).orElse(null);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(adoption.getBotUser().getChatId());
        sendMessage.setText(text);
        sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        adoptionRepository.delete(adoption);
        petRepository.delete(adoption.getPet());
    }

    @Override
    public boolean isSupport(String command) {
        try {
            String[] parts = command.split("-");
        } catch (Exception e) {
            return false;
        }
        String[] parts = command.split("-");
        return parts[0].equals("доп") || parts[0].equals("неудачный");
    }
}
