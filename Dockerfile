# Используем базовый образ с OpenJDK 17 (или другой версии, соответствующей вашему проекту)
FROM openjdk:17-jdk-slim

# Копируем собранный jar-файл в контейнер и переименовываем его в app.jar
COPY target/messenger-0.0.1-SNAPSHOT.jar app.jar

# Указываем, что контейнер прослушивает порт 8080
EXPOSE 8080

# Команда запуска приложения
ENTRYPOINT ["java", "-jar", "/app.jar"]
