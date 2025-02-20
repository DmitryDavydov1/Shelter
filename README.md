# Shelter-Bot (Бот для приюта)

**Shelter-Bot**  - бот для приюта животных, позволяет:
- Взять животное на усыновление
- Просматривать отчеты усыновителей волонтерами
- Получать инфомацию о приюте
- Добавлять новых животных, в приют
- Предоставляет удобную админку для волонтеров


## Как запустить:
- Получите **Токен Бота** в [BotFather](https://t.me/BotFather)
- **Настройка проекта**: В [application properies](https://github.com/DmitryDavydov1/Shelter/blob/master/src/main/resources/application.properties) настройте параметры:
  - в `bot.name` укажите имя вашего бота
  - в `bot.token` вставьте токен вашего бота
  - в `spring.datasource.url` url вашей БД
  - в `spring.datasource.username` имя баззы данных
  - в `spring.datasource.password` пароль от бд
  - в `number.phone.admin` номер телефона вашего админа(для входа в админку)
  - в `tg.id.admin` tg - id вашего волонтера(для конактных данных)


## API:
### Питомцы

`POST /pet `


- Описание: Добавление новых питомцев
- тело:
  - `nickname` кличка питомца
  - `age` возраст питомца
  - `weight` вес питомца 
  - `gender` вес питомца
  - `height` рост питомца



