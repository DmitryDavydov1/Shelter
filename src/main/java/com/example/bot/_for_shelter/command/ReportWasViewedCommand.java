package com.example.bot._for_shelter.command;


import com.example.bot._for_shelter.model.AdoptionDTO;
import com.example.bot._for_shelter.model.PhotoTg;
import com.example.bot._for_shelter.repository.PhotoTgRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReportWasViewedCommand implements Command {

    private final PhotoTgRepository photoTgRepository;

    public ReportWasViewedCommand(PhotoTgRepository photoTgRepository) {
        this.photoTgRepository = photoTgRepository;
    }

    @Override
    public void execute(Update update) {
//        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        String command = update.getCallbackQuery().getData();
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(command);
//        String messageId = String.valueOf(update.getMessage().getMessageId());

        if (matcher.find()) {

            Long reportId = Long.valueOf(matcher.group(2));
            PhotoTg photoTg = photoTgRepository.findById(reportId).orElse(null);
            photoTg.setViewed(true);
            photoTgRepository.save(photoTg);
        } else {

            System.out.println("No match found for command: " + command);
        }
    }

    @Override
    public boolean isSupport(String command) {
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String group1 = matcher.group(1);
            return group1.equals("Просмотрел-");
        } else {
            // Логируем или обрабатываем случай, когда совпадение не найдено
            System.out.println("No match found for command: " + command);
        }

        return false;
    }
}
