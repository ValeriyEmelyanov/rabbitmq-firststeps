# RabbitMQ: first steps
Учебный проект.

### В проекте используются
* Java 11
* RabbitMQ
* Maven

### Работа с RabbitMQ в контейнере Docker
Создать и стартовать контейнер

```
docker run -d -p 15672:15672 
              -p 5672:5672 
              -p5671:5671 
              --hostname rabbitmq 
              --name rabbitmq-container
              rabbitmq:3.9-management
```

Открыть консоль администрирования в браузере

```
http://localhost:15672
```
*логин: guest, пароль: guest*

Старт и остановка созданного контейнера

```
docker start rabbitmq-container
docker stop rabbitmq-container
```

Удаление контейнера

```
docker rm rabbitmq-container
```

Запустить bash в контейнере

```
docker exec -it rabbitmq-container /bin/bash
```

Завершить работу в bash

```
exit
```

### Sender - Receiver
Sender посылает сообщения в очередь, 
Reciever получает сообщения из очереди вывод их на экран.

1. Запустить программу Receiver, которая ждет сообщения из очереди.
2. Запустить программу Sender, которая посылает в очередь 10 сообщений.
