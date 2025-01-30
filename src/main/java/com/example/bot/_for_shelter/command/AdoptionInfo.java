package com.example.bot._for_shelter.command;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.takeAnimal;

@Component
public class AdoptionInfo implements Command {

    @Autowired
    SendBotMessageServiceImpl sendBotMessageService;

    @Override
    public void execute(Update update) {
        String shelterInfo = "Обычно животных из приюта берут нахуй";

        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId())).text(shelterInfo).build();

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();

        var backButton = new InlineKeyboardButton();
        String backButtonButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        backButton.setText(backButtonButtonText);
        backButton.setCallbackData("dog-button");

        rowInLine1.add(backButton);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);

        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        sendBotMessageService.sendMessage(sendMessage, messageId);

    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(takeAnimal.getCommandName());
    }
}
