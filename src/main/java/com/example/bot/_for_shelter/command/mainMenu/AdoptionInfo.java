package com.example.bot._for_shelter.command.mainMenu;

import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.contactData;
import static com.example.bot._for_shelter.command.CommandName.takeAnimal;

@Component
public class AdoptionInfo implements Command {

    @Autowired
    SendBotMessageServiceImpl sendBotMessageService;

    @Override
    public void execute(Update update) {
        String shelterInfo = "Выберите команду";

        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId())).text(shelterInfo).build();

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();

        var backButton = new InlineKeyboardButton();
        String backButtonButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        backButton.setText(backButtonButtonText);
        backButton.setCallbackData("dog-button");


        var informationButton = new InlineKeyboardButton();
        String informationButtonButtonText = EmojiParser.parseToUnicode("Информация для клиента");
        informationButton.setText(informationButtonButtonText);
        informationButton.setCallbackData("/info-2");

        var takeAnimal = new InlineKeyboardButton();
        String takeAnimalButtonText = EmojiParser.parseToUnicode("Вот список животных" + " :dog2:");
        takeAnimal.setText(takeAnimalButtonText);
        takeAnimal.setCallbackData("/takeAnimal");

        var writeContact = new InlineKeyboardButton();
        String writeContactButtonText = EmojiParser.parseToUnicode("записать контактные данные");
        writeContact.setText(writeContactButtonText);
        writeContact.setCallbackData(contactData.getCommandName());

        rowInLine1.add(backButton);
        rowInLine2.add(takeAnimal);
        rowInLine3.add(writeContact);
        rowInLine4.add(informationButton);
        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);
        rowsInLine.add(rowInLine4);
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
