package com.example.bot._for_shelter.command.forAdmin;


import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.service.PhotoTgService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReportWasViewedCommand implements Command {

    private final PhotoTgService photoTgService;

    public ReportWasViewedCommand(PhotoTgService photoTgService) {
        this.photoTgService = photoTgService;
    }

    @Override
    public void execute(Update update) {
        String command = update.getCallbackQuery().getData();
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            Long reportId = Long.valueOf(matcher.group(2));
            photoTgService.setsView(reportId);
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
            System.out.println("No match found for command: " + command);
        }

        return false;
    }
}
