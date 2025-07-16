Мессенджер

Обзор

Полнофункциональное веб-приложение-мессенджер для обмена сообщениями в режиме реального времени.

Фронтенд: React + TypeScript

Бэкенд: Spring Boot (Java)

База данных: PostgreSQL

Контейнеризация: Docker & Docker Compose

Реальное время: WebSocket / STOMP (SockJS)

Аутентификация: JWT (JSON Web Token)

Возможности

Регистрация пользователей и вход через JWT

Создание и список чатов

Отправка и получение сообщений в реальном времени по WebSocket/STOMP

REST‑эндпоинты для управления чатами и получения истории сообщений

Docker Compose для простого развертывания

Стек технологий

Слой

Технологии

Фронтенд

React, TypeScript, SockJS, STOMP.js

Бэкенд

Spring Boot, Spring Security, JWT

База данных

PostgreSQL

Реальное время

WebSocket, STOMP, SockJS

Контейнеризация

Docker, Docker Compose

Запуск локально

Необходимые инструменты

Java 17+

Maven

Node.js & npm

Docker & Docker Compose

Шаги

Клонировать репозиторий

git clone https://github.com/yourusername/messenger.git
cd messenger

Запустить бэкенд и базу данных

docker compose up --build

Бэкенд доступен по http://localhost:8080

PostgreSQL слушает на localhost:5432 (БД: messenger_db, пользователь: postgres)

Запустить фронтенд

cd client
npm install
npm start

Фронтенд доступен по http://localhost:3000

REST‑API

Аутентификация

POST /register — регистрация нового пользователя

{
  "username": "alice",
  "password": "P@ssw0rd"
}

Ответ: JWT-токен и данные пользователя

POST /login — вход существующего пользователя

{
  "username": "alice",
  "password": "P@ssw0rd"
}

Ответ: JWT-токен

Пользователи

GET /users — получить список всех пользователей

Чаты

POST /chats/create — создать новый чат

{
  "participants": [
    { "id": 1 },
    { "id": 2 }
  ]
}

Ответ: объект Chat с id и списком участников

GET /chats/user/{userId} — получить все чаты пользователя с userId

Сообщения (REST)

POST /messages/send — отправить сообщение

Параметры запроса: senderId, chatId, text

Заголовок: Authorization: Bearer <token>

GET /messages/chat/{chatId} — история сообщений чата

Заголовок: Authorization: Bearer <token>

WebSocket / STOMP

Endpoint: http://localhost:8080/ws (SockJS)

Префикс для отправки: /app

@MessageMapping: /app/send

Топик для подписки: /topic/chat/{chatId}

// Пример тела сообщения
{
  senderId: 1,
  chatId: 4,
  text: "Привет, мир!"
}

Docker Compose

version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/messenger_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: your_password
    depends_on:
      - db

  db:
    image: postgres:14
    environment:
      POSTGRES_DB: messenger_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: your_password
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:

CI/CD (опционально)

Сборка и тесты бэкенда: mvn clean package

Сборка Docker-образов

Публикация в Docker Registry

Автоматическое развёртывание на VPS

Лицензия

MIT © Ваше Имя

