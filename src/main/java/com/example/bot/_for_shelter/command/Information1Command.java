package com.example.bot._for_shelter.command;

import com.vdurmont.emoji.EmojiParser;
import org.aspectj.bridge.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.menuForInformation;
import static com.example.bot._for_shelter.command.CommandName.takeAnimal;

@Component
public class Information1Command implements Command {

    private final SendBotMessageServiceImpl sendBotMessageService;

    public Information1Command(SendBotMessageServiceImpl sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

        String text = text();

        SendMessage message = new SendMessage();

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();

        var backButton = new InlineKeyboardButton();
        String backButtonButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        backButton.setText(backButtonButtonText);
        backButton.setCallbackData(menuForInformation.getCommandName());

        rowInLine1.add(backButton);
        rowsInLine.add(rowInLine1);
        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);


        message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        message.setText(text);

        sendBotMessageService.sendMessage(message, Math.toIntExact(update.getCallbackQuery().getMessage().getMessageId()));
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals("/info-1");
    }

    public String text() {
        String message = """
                

                1. 👋 *Приветствие* \s
                Привет! 😊 Добро пожаловать! Я помогу вам разобраться с усыновлением питомцев и расскажу всё о нашем приюте. Чем могу помочь? 🐶🐱 \s
                
                2. 🏠 *О приюте* \s
                Наш приют — это место, где животные получают второй шанс на счастливую жизнь. Мы заботимся о бездомных и брошенных питомцах, лечим их и подбираем для них любящие семьи. \s
                
                💖 Здесь работают волонтёры, кинологи и ветеринары, чтобы каждый хвостик нашёл дом! \s
                
                3. 🕒 *Расписание работы, адрес и проезд* \s
                📍 *Адрес: Улица Чиловая 41 \s
                🕘 *График работы:* \s
                🔹 Пн–Пт: 10:00 – 18:00 \s
                🔹 Сб–Вс: 11:00 – 17:00 \s
                
                📌 Пожалуйста, уточняйте перед визитом, так как график может меняться! \s
                
                4. 📞 +79871795342 \s
                Если вы планируете въезд на территорию приюта на машине, необходимо оформить пропуск. \s
                
                ☎ *Телефон охраны: +79871795342 \s
                📌 Позвоните заранее, чтобы избежать задержек на КПП. \s
                
                5. ⚠️ *Техника безопасности на территории приюта* \s
                Чтобы ваш визит был безопасным, соблюдайте правила: \s
                ✔ Всегда следуйте указаниям сотрудников приюта. \s
                ✔ Не подходите к вольерам без разрешения. \s
                ✔ Держите личные вещи при себе. \s
                ✔ Если идёте с детьми, следите за ними. \s
                
                🐾 Безопасность важна как для вас, так и для наших питомцев! \s      
                """;
        return message;

    }
}
