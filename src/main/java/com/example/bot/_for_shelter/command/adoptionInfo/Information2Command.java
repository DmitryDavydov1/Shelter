package com.example.bot._for_shelter.command.adoptionInfo;

import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.takeAnimal;

@Component
public class Information2Command implements Command {

    private final SendBotMessageServiceImpl sendBotMessageService;

    public Information2Command(SendBotMessageServiceImpl sendBotMessageService) {
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
        backButton.setCallbackData(takeAnimal.getCommandName());

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
        return command.equals("/info-2");
    }

    public String text() {
        String message = """
                
                1. 🐾 *Правила знакомства с животным*  
                Перед тем как забрать питомца домой, важно правильно с ним познакомиться:  
                ✔ Дайте ему время привыкнуть к вашему присутствию.  
                ✔ Не навязывайтесь, позвольте подойти первому.  
                ✔ Используйте спокойный голос и медленные движения.  
                ✔ Возьмите с собой угощение, чтобы завоевать доверие.  
                ⏳ Посещение может занять несколько встреч — будьте терпеливы!
                
                3. 📄 *Документы для усыновления*  
                Чтобы оформить усыновление, понадобятся:  
                ✔ Паспорт (для подтверждения личности).  
                ✔ Договор об ответственном содержании (оформляется в приюте).  
                ✔ Доказательство стабильного дохода (может потребоваться в некоторых случаях).  
                ✔ Фото или видео условий проживания (по запросу).  
                📌 Уточняйте подробности у сотрудников приюта!
                
                4. 🚗 *Рекомендации по транспортировке*  
                Чтобы переезд был комфортным для питомца:  
                ✔ Используйте переноску (для кошек и мелких собак).  
                ✔ Для крупных собак — шлейку и ремень безопасности.  
                ✔ Не открывайте окна, чтобы избежать побега.  
                🐶 Дайте время привыкнуть к новому месту после дороги!
                
                5. 🏡 *Обустройство дома для щенка*  
                Щенку нужно безопасное пространство:  
                ✔ Уберите провода, мелкие предметы и химикаты.  
                ✔ Купите мягкую лежанку, миски и игрушки.  
                ✔ Подготовьте место для туалета (пеленки).  
                ✔ Определите зону отдыха и кормления.  
                ✔ Купите корм, подходящий для возраста щенка.  
                🐾 Щенки активны — обеспечьте им внимание и ласку!
                
                6. 🐕 *Обустройство дома для взрослого животного*  
                Взрослые питомцы привыкают к новому дому постепенно:  
                ✔ Обустройте тихое место для отдыха.  
                ✔ Подготовьте корм, подходящий по возрасту.  
                ✔ Ограничьте доступ к опасным местам.  
                ✔ Дайте время адаптироваться, не торопите знакомство.  
                ❤️ Терпение и забота помогут наладить доверие!
                
                7. 🐾 *Обустройство дома для животного с ОВЗ*  
                Животным с ограниченными возможностями нужен особый уход:  
                ✔ Для незрячих — минимизируйте перестановки мебели.  
                ✔ Для маломобильных — коврики для лучшего сцепления лап.  
                ✔ Для глухих — больше визуальных сигналов.  
                ✔ Подготовьте специальный корм и удобное место для отдыха.  
                🐕‍🦺 Важно создать комфорт и безопасность для нового друга!
                
                8. 🤝 *Советы кинолога по первичному общению*  
                Чтобы питомец быстрее привык к вам:  
                ✔ Говорите спокойно, не делайте резких движений.  
                ✔ Позвольте ей самой подойти к вам.  
                ✔ Используйте вкусняшки для укрепления доверия.  
                🐕 Первые впечатления важны — будьте терпеливы и добры!
                
                
                10. ⚠️ *Причины отказа в усыновлении*  
                Приют может отказать в передаче животного, если:  
                ❌ Вы не соответствуете требованиям (возраст, условия жилья).  
                ❌ У вас ранее были случаи жестокого обращения с животными.  
                ❌ Вы не готовы предоставить необходимые документы.  
                ❌ У вас нет стабильного дохода для ухода за питомцем.  
                
                """;
        return message;

    }
}
