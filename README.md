![Bot](https://github.com/cyberpunkoff/tracktor/actions/workflows/bot.yml/badge.svg)
![Scrapper](https://github.com/cyberpunkoff/tracktor/actions/workflows/scrapper.yml/badge.svg)

# 🚜🔗 track*tor*

Приложение для отслеживания обновлений контента по ссылкам.
При появлении новых событий отправляется уведомление в Telegram.

ФИО: `Ластин Василий Ильич`

Проект написан на `Java 21` с использованием `Spring Boot 3`.

Проект состоит из 2-х приложений:
* Bot
* Scrapper

Переменные среды задаются в файле `.env`. Пример файла приведен в `.env.example`.

Для работы требуется БД `PostgreSQL`. Присутствует опциональная зависимость на `Kafka`.
