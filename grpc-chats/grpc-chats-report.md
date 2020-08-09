# Разделение монолита на сервисы

1) Вынести систему диалогов в отдельный сервис  
  - 
    - 
```shell script
wget https://github.com/grpc-swagger/grpc-swagger/releases/latest/download/grpc-swagger.jar
java -jar grpc-swagger.jar --server.port=8889
```


2) Взаимодействия монолитного сервиса и сервиса чатов реализовать на Rest API или gRPC.
3) Организовать сквозное логирование запросов.
4) Предусмотреть то, что не все клиенты обновляют приложение быстро и кто-то может ходить через старое API.  





### Полезные ссылки
- DockerHub MondoDB - https://hub.docker.com/_/mongo
- Grpc Swagger - https://github.com/grpc-swagger/grpc-swagger
