package com.example.bot._for_shelter.buttons;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class Buttons {

    public InlineKeyboardMarkup sendMenu(long chatId) {
        String text = "Выберите пункт";
        SendMessage message = sendMessage(chatId, text);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine5 = new ArrayList<>();
        var informationButton = new InlineKeyboardButton();
        String informationButtonText = EmojiParser.parseToUnicode("Узнать информацию о приюте" + " :information_source:");

        informationButton.setText(informationButtonText);
        informationButton.setCallbackData("information-button");

        var takeAnimalButton = new InlineKeyboardButton();
        String takeAnimalButtonText = EmojiParser.parseToUnicode("Как взять животное из приюта " + " :cat:");
        takeAnimalButton.setText(takeAnimalButtonText);
        takeAnimalButton.setCallbackData("takeAnimal-button");

        var petReportButtonButton = new InlineKeyboardButton();
        String petReportButtonText = EmojiParser.parseToUnicode("Прислать отчет о питомце" + " :dog:");
        petReportButtonButton.setText(petReportButtonText);
        petReportButtonButton.setCallbackData("petReport-button");

        var callVolunteerButton = new InlineKeyboardButton();
        String callVolunteerButtonText = EmojiParser.parseToUnicode("Позвать волонтера" + " :boy:");
        callVolunteerButton.setText(callVolunteerButtonText);
        callVolunteerButton.setCallbackData("callVolunteer-button");

        var backButton = new InlineKeyboardButton();
        String backButtonButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        backButton.setText(backButtonButtonText);
        backButton.setCallbackData("back-menu-button");

        rowInLine1.add(informationButton);
        rowInLine2.add(petReportButtonButton);
        rowInLine3.add(takeAnimalButton);
        rowInLine4.add(callVolunteerButton);
        rowInLine5.add(backButton);


        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);
        rowsInLine.add(rowInLine4);
        rowsInLine.add(rowInLine5);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);


        return markupInLine;
    }

    public SendMessage sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        return message;
    }

    public SendMessage startCommandReceived(long chatId, String name) {
        String answer = "Hi " + name + ", nice to meet you";
        SendMessage message = sendMessage(chatId, answer);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var dogButton = new InlineKeyboardButton();
        String dogButtonText = EmojiParser.parseToUnicode("Собаки" + " :dog:");

        dogButton.setText(dogButtonText);
        dogButton.setCallbackData("dog-button");

        var catButton = new InlineKeyboardButton();
        String catButtonText = EmojiParser.parseToUnicode("Кошки" + " :cat:");
        catButton.setText(catButtonText);
        catButton.setCallbackData("cat-button");

        rowInLine.add(dogButton);
        rowInLine.add(catButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        return message;
    }


    public SendMessage menuForInformation() {
        SendMessage sendMessage = new SendMessage();
        String answer = "Добро пожаловать в приют для животных";
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

        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);
        rowsInLine.add(rowInLine1);

        markupInLine.setKeyboard(rowsInLine);


        sendMessage.setReplyMarkup(markupInLine);

        return sendMessage;

    }

}
