package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.command.*;
import com.example.bot._for_shelter.command.mainMenu.WriteReportToBd;
import com.example.bot._for_shelter.config.BotConfig;
import com.example.bot._for_shelter.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.writeContactAtBd;

/**
 * Класс, представляющий бота Telegram для взаимодействия с пользователями, обработки команд и сообщений.
 * Выполняет команды, проверяет сообщения и обновления, а также выполняет плановые задачи.
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    /**
     * Конфигурация бота
     */
    final BotConfig config;

    /**
     * Список всех доступных команд бота
     */
    private final List<Command> commandList;

    /**
     * Репозиторий для работы с пользователями
     */
    private final UserRepository userRepository;

    /**
     * Сервис для записи отчетов в базу данных
     */
    private final WriteReportToBd writeReportToBd;

    /**
     * Сервис для работы с процессом усыновления животных
     */
    private final AdoptionService adoptionService;

    /**
     * Конструктор для инициализации бота.
     *
     * @param config          Конфигурация бота
     * @param commandList     Список команд, которые поддерживает бот
     * @param userRepository  Репозиторий пользователей
     * @param writeReportToBd Сервис для записи отчетов
     * @param adoptionService Сервис для усыновления животных
     */
    public TelegramBot(BotConfig config,
                       List<Command> commandList,
                       UserRepository userRepository,
                       WriteReportToBd writeReportToBd,
                       AdoptionService adoptionService) {
        this.config = config;
        this.commandList = commandList;
        this.userRepository = userRepository;
        this.writeReportToBd = writeReportToBd;
        this.adoptionService = adoptionService;
    }

    /**
     * Возвращает имя бота.
     *
     * @return Имя бота
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    /**
     * Возвращает токен бота.
     *
     * @return Токен бота
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Обрабатывает обновления от пользователей (сообщения, команды и callback-запросы).
     *
     * @param update Обновление от Telegram API
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            writeReportToBd.execute(update);
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            boolean commandExecuted = commandList.stream()
                    .filter(command -> command.isSupport(update.getMessage().getText()))
                    .findAny() // Находит любую подходящую команду
                    .map(command -> {
                        command.execute(update); // Выполняет команду
                        return true; // Возвращает true, если команда была выполнена
                    })
                    .orElse(false);
            if (!commandExecuted) {
                String chatId = update.getMessage().getChatId().toString();
                String condition = userRepository.findByChatId(chatId).getCondition();
                if (!condition.equals("default")) {
                    writeReportToBd.execute(update);
                }
            }
        }

        if (update.hasCallbackQuery()) {
            commandList.stream()
                    .filter(command -> command.isSupport(update.getCallbackQuery().getData()))
                    .forEach(command -> {
                        command.execute(update);
                    });
        }

        if (update.hasMessage() && update.getMessage().hasContact()) {
            commandList.stream()
                    .filter(command -> command.isSupport(writeContactAtBd.getCommandName()))
                    .forEach(command -> {
                        command.execute(update);
                    });
        }
    }

    /**
     * Плановое выполнение задачи добавления дня в счетчик усыновления.
     * Задача выполняется каждый день в полдень.
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void addDayInCounter() {
        System.out.println("Я выполняю");
        adoptionService.addOneDay();
    }

    /**
     * Отправляет фото пользователю через Telegram API.
     *
     * @param sendPhoto Объект SendPhoto, содержащий информацию о фото
     */
    public void sendPhoto(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
