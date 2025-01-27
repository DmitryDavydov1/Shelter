package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.model.PhotoTg;
import com.example.bot._for_shelter.repository.PhotoTgRepository;
import com.example.bot._for_shelter.service.TelegramBot;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.WatchReportsByAdmin;

@Component
public class WatchReports implements Command {
    private final PhotoTgRepository photoTgRepository;
    private final SendBotMessageService sendBotMessageService;
    private final TelegramBot telegramBot;
    @Autowired
    @Lazy
    public WatchReports(PhotoTgRepository photoTgRepository, SendBotMessageService sendBotMessageService, TelegramBot telegramBot) {
        this.photoTgRepository = photoTgRepository;
        this.sendBotMessageService = sendBotMessageService;
        this.telegramBot = telegramBot;
    }

    @Override
    public void execute(Update update) {
        List<PhotoTg> all = photoTgRepository.findAll();
        all.forEach(photoTg -> {
            String chatId = update.getMessage().getChatId().toString();
            SendMessage sendMessage = new SendMessage();
            InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
            List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();

            String informationButtonText = EmojiParser.parseToUnicode("Прислать предупреждение" + " :cat2:");
            var warningButton = new InlineKeyboardButton();
            warningButton.setText(informationButtonText);
            warningButton.setCallbackData("просмотрел");
            var viewedIt = new InlineKeyboardButton();
            viewedIt.setText("Просмотрел");
            viewedIt.setCallbackData("просмотрел" + photoTg.getId());
            rowInLine1.add(warningButton);
            rowInLine1.add(viewedIt);
            rowsInLine.add(rowInLine1);
            markupInLine.setKeyboard(rowsInLine);
            sendMessage.setReplyMarkup(markupInLine);
            sendMessage.setText(photoTg.getReport().getText());
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            SendPhoto msg = SendPhoto
                    .builder()
                    .chatId(chatId)
                    .photo(new InputFile(photoTg.getFileId()))
                    .build();
            telegramBot.sendPhoto(msg);
            sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        });
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(WatchReportsByAdmin.getCommandName());
    }
}
