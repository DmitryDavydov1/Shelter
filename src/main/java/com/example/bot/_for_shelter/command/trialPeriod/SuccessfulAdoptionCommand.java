package com.example.bot._for_shelter.command.trialPeriod;

import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageService;
import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.repository.PetRepository;

import com.example.bot._for_shelter.service.AdoptionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class SuccessfulAdoptionCommand implements Command {


    private final SendBotMessageService sendBotMessageService;
    private final PetRepository petRepository;
    private final AdoptionService adoptionService;

    public SuccessfulAdoptionCommand(SendBotMessageService sendBotMessageService, PetRepository petRepository, AdoptionService adoptionService) {
        this.sendBotMessageService = sendBotMessageService;
        this.petRepository = petRepository;
        this.adoptionService = adoptionService;
    }

    public String text = """
            Поздравляю вы успешно прошли испытательный срок и теперь вы законный владелец питомца
            """;

    @Override
    @Transactional
    public void execute(Update update) {
        String callbackText = update.getCallbackQuery().getData();
        String[] parts = callbackText.split("-");
        Adoption adoption = adoptionService.findByChatId(Integer.parseInt(parts[2]));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(adoption.getBotUser().getChatId());
        sendMessage.setText(text);
        sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        adoptionService.deleteAdoption(adoption);
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
        return parts[0].equals("успешный");
    }
}
