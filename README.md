# ПРОЕКТ "ПОГОДА"

_Веб-приложение - трекер_ для просмотра текущей погоды.

![Screenshot_38.png](assets/Screenshot_38.png)

_Функциональность:_
Приложение отображает погоду для выбранных пользователем локаций.
Для отображения локаций пользователю необходимо зарегистрироваться с произвольным логином, выбрать через поиск и подписаться на необходимые для него локации, после чего прогноз погоды для выбранных локаций будет отображаться на главной странице.

Данные прогноза запрашиваются у сервиса OpenWeatherMap (https://openweathermap.org/) по API.

#### Сервисы приложения для пользователя:
* Регистрация
* Вход в учетную запись
* Выход из учетной записи

#### Сервисы приложения для отображения погоды:
* Поиск локаций
* Добавление локации в список прогноза погоды
* Удаление локации из списка прогноза погоды
* Отображение списка локаций с прогнозом погоды

#### Используемые технологии при разработке:
* Фронт - Bootstrap 5.0
* Бэк - Java (Maven, Jakarta servlet, Lombok, Thymeleaf, ModelMapper)
* База данных - Postgres + Hibernate + FlyWay
* Деплой на сервер - Docker

#### Используемые технологии при тестах:
* Для слоя работы с базой данных - Testcontainers (https://testcontainers.com/)
* Для слоя работы с внешним API - WireMock (https://wiremock.org/)

Установка:
* Загрузить приложение, выполнить тестовый запуск на локальной машине запустив docker-compose
* Для функционирования приложение необходимо довабить переменные окружения, например , при помощи .env файла: 
   POSTGRES_USER=user / POSTGRES_PASSWORD=password с вашими user/password
* Далее выполнить деплой Docker контейнеров на удаленный сервер.


