# Разделение монолита на сервисы

1) Вынести систему диалогов в отдельный сервис  
- Отдельный сервис для диалогов [social-chat](../social-chat)
- Вызов сервиса производится в сервисе [social-backend](../social-backend)
- Про устройство системы диалогов [Шардинг диалогов](../sharding/sharding-report.md)

2) Взаимодействия монолитного сервиса и сервиса чатов реализовать на gRPC.
- Proto файл для взаимодействия с сервисом [social-chat proto](../social-chat/src/main/resources/proto)
- Для генерации клиента/сервера GRPC использован Maven плагин
```xml
<plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
</plugin>
```
- Для генерации swagger документации чз reverse proxy использован плагин
```shell script
wget https://github.com/grpc-swagger/grpc-swagger/releases/latest/download/grpc-swagger.jar
java -jar grpc-swagger.jar --server.port=8889
```

### Полезные ссылки
- DockerHub MondoDB - https://hub.docker.com/_/mongo
- Grpc Swagger - https://github.com/grpc-swagger/grpc-swagger
- Ресурсы по GRPC - https://github.com/grpc-ecosystem/awesome-grpc
