package com.example.bot._for_shelter.command;


import com.example.bot._for_shelter.model.PhotoTg;
import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.model.ReportDTO;
import com.example.bot._for_shelter.repository.PhotoTgRepository;
import com.example.bot._for_shelter.repository.ReportRepository;
import com.example.bot._for_shelter.repository.UserRepository;
import com.example.bot._for_shelter.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.Comparator;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.WriteReportToBd1;

@Component
public class WriteReportToBd implements Command {

    private final UserService userService;
    private final ReportService reportService;
    private final SendBotMessageService sendBotMessageService;
    private final TelegramBot telegramBot;
    private final ReportRepository reportRepository;
    private final PhotoTgService photoTgService;
    private final AdoptionService adoptionService;


    @Autowired
    @Lazy
    public WriteReportToBd(UserService userService, ReportService reportService, SendBotMessageService sendBotMessageService, TelegramBot telegramBot,
                           ReportRepository reportRepository, PhotoTgService photoTgService, AdoptionService adoptionService) {
        this.userService = userService;
        this.reportService = reportService;
        this.sendBotMessageService = sendBotMessageService;

        this.telegramBot = telegramBot;
        this.reportRepository = reportRepository;
        this.photoTgService = photoTgService;
        this.adoptionService = adoptionService;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()) {
            SendMessage message = new SendMessage();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (!adoptionService.findByChatId(chatId)) {
                message.setChatId(String.valueOf(chatId));
                message.setText("Сначала усынови кого-то");
                sendBotMessageService.sendMessageWithKeyboardMarkup(message);
                return;
            }
            if (reportRepository.existsByChatIdAndHavePhoto(String.valueOf(chatId), false)) {
                message.setText("прежде чем отправлять фотку для нового репорта, сначала отправь фотку для прошлого");
                message.setChatId(String.valueOf(chatId));
                sendBotMessageService.sendMessageWithKeyboardMarkup(message);
                return;
            }
            userService.changeCondition(chatId, "report");
            message.setChatId(String.valueOf(chatId));
            message.setText("Напиши свой текстовый отчет");
            sendBotMessageService.sendMessageWithKeyboardMarkup(message);


        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            ReportDTO report = new ReportDTO();
            report.setText(text);
            report.setChatId(String.valueOf(chatId));
            reportService.addReport(report);
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Пришлите фотку");
            sendBotMessageService.sendMessageWithKeyboardMarkup(message);
        }
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            long chat_id = update.getMessage().getChatId();
            List<PhotoSize> photos = update.getMessage().getPhoto();
            String f_id = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getFileId)
                    .orElse("");
            System.out.println(f_id.length());
            SendPhoto msg = SendPhoto
                    .builder()
                    .chatId(String.valueOf(chat_id))
                    .photo(new InputFile(f_id))
                    .build();
            Report report = reportRepository.findByChatIdAndHavePhoto(String.valueOf(chat_id), false);
            photoTgService.addPhotoTg(String.valueOf(chat_id), f_id, report);

            report.setHavePhoto(true);
            reportRepository.save(report);
            userService.changeCondition(chat_id, "default");
            telegramBot.sendPhoto(msg);
        }
    }


    @Override
    public boolean isSupport(String command) {
        return command.equals(WriteReportToBd1.getCommandName());
    }
}

