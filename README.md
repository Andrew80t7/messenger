# 📬 **Мессенджер**  
*Полнофункциональное веб-приложение для обмена сообщениями в режиме реального времени*

---

## 🔍 Обзор
Мощный мессенджер с современным стеком технологий:
- **Фронтенд**: React + TypeScript  
- **Бэкенд**: Spring Boot (Java)  
- **База данных**: PostgreSQL  
- **Контейнеризация**: Docker & Docker Compose  
- **Реальное время**: WebSocket / STOMP (SockJS)  
- **Аутентификация**: JWT  

---

## ✨ Возможности
- ✅ Регистрация пользователей и вход через JWT
- ✅ Создание чатов и управление списком контактов
- ⚡ Отправка/получение сообщений в реальном времени
- 🌐 REST API для управления данными
- 🐳 Простое развертывание через Docker Compose

---

## 🛠 Технологический стек
| Слой         | Технологии                                                                 |
|--------------|----------------------------------------------------------------------------|
| **Фронтенд** | React, TypeScript, SockJS, STOMP.js, Axios                                 |
| **Бэкенд**   | Spring Boot, Spring Security, Spring Data JPA, JWT, WebSocket              |
| **База данных** | PostgreSQL                                                               |
| **Инфраструктура** | Docker, Docker Compose                                                |

---

## 🚀 Быстрый старт
### 📋 Предварительные требования
- Java 17+
- Maven
- Node.js ≥16 + npm
- Docker & Docker Compose

### 🔧 Запуск проекта
```bash
# Клонировать репозиторий
git clone https://github.com/yourusername/messenger.git
cd messenger

# Запустить бэкенд и БД
docker compose up --build -d

# Запустить фронтенд
cd client
npm install
npm start
