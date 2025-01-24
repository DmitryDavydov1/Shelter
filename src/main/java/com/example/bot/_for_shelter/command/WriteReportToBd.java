package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.model.ReportDTO;
import com.example.bot._for_shelter.service.ReportService;
import com.example.bot._for_shelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.bot._for_shelter.command.CommandName.WriteReportToBd1;
import static com.example.bot._for_shelter.command.CommandName.writeContactAtBd;

@Component
public class WriteReportToBd implements Command {

    private final UserService userService;
    private final ReportService reportService;
    private final SendBotMessageService sendBotMessageService;

    public WriteReportToBd(UserService userService, ReportService reportService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.reportService = reportService;
        this.sendBotMessageService = sendBotMessageService;
    }


    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            userService.changeCondition(chatId, "report");
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Напиши своей текстовый отчет");
            sendBotMessageService.sendMessageWithKeyboardMarkup(message);
        }
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            ReportDTO report = new ReportDTO();
            report.setText(text);
            report.setChatId(String.valueOf(chatId));
            reportService.addReport(report);
        }

    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(WriteReportToBd1.getCommandName());
    }
}
