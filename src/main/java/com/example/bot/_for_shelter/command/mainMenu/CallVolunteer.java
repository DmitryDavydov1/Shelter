package com.example.bot._for_shelter.command.mainMenu;

import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.callVolunteer;

@Component
public class CallVolunteer implements Command {


    private final SendBotMessageService sendBotMessageService;


    public CallVolunteer(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        message.setText("Id волонтера- @Butterfl_1y");
        int messageId = update.getCallbackQuery().getMessage().getMessageId();


        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();


        var informationButton = new InlineKeyboardButton();
        String informationButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        informationButton.setText(informationButtonText);
        informationButton.setCallbackData("dog-button");


        rowInLine1.add(informationButton);


        rowsInLine.add(rowInLine1);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        sendBotMessageService.sendMessage(message, messageId);

    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(callVolunteer.getCommandName());
    }
}
