package com.example.bot._for_shelter.command;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MenuForInformationCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public MenuForInformationCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;


    }

    @Override
    public void execute(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        String answer = "Добро пожаловать в приют для животных";
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(answer);


        var backButton = new InlineKeyboardButton();
        backButton.setCallbackData("back-information-button");
        String backButtonButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        backButton.setText(backButtonButtonText);

        var informationAboutShelterButton = new InlineKeyboardButton();
        informationAboutShelterButton.setCallbackData("information-about-shelter-button");
        String informationAboutShelterButtonText = EmojiParser.parseToUnicode("Информация о приюте");
        informationAboutShelterButton.setText(informationAboutShelterButtonText);

        var timetableAndAddressAndDrivingDirectionsButton = new InlineKeyboardButton();
        timetableAndAddressAndDrivingDirectionsButton.setCallbackData("timetable-and-address-and-driving-directions-button");
//        String timetableAndAddressAndDrivingDirectionsButtonText = EmojiParser.parseToUnicode("Работаем с 8 до 19,\n Адрес - Улица нурстултана Назарбаева \n Cхема проезда:");
        String timetableAndAddressAndDrivingDirectionsButtonText = EmojiParser.parseToUnicode("График, Адрес, Схема проезда");
        timetableAndAddressAndDrivingDirectionsButton.setText(timetableAndAddressAndDrivingDirectionsButtonText);


        var contactInformationButton = new InlineKeyboardButton();
        contactInformationButton.setCallbackData("contact-information-button");
        String contactInformationButtonButtonText = EmojiParser.parseToUnicode("Записать контактные данные :card_file_box:");
        contactInformationButton.setText(contactInformationButtonButtonText);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine5 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine6 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine7 = new ArrayList<>();

        rowInLine1.add(backButton);
        rowInLine2.add(informationAboutShelterButton);
        rowInLine3.add(timetableAndAddressAndDrivingDirectionsButton);
        rowInLine4.add(contactInformationButton);

        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);
        rowsInLine.add(rowInLine4);
        rowsInLine.add(rowInLine1);

        markupInLine.setKeyboard(rowsInLine);


        sendMessage.setReplyMarkup(markupInLine);
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        sendBotMessageService.sendMessage(sendMessage, messageId);
    }
}
