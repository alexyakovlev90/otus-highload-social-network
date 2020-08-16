# Внедрение docker и consul

1) Обернуть сервис диалогов в docker
  - [Dockerfile](../social-chat/Dockerfile) сервиса диалогов - [ay-social-chat](https://hub.docker.com/repository/docker/alexyakovlev90/ay-social-chat)
  - [Dockerfile](../Dockerfile) основного бэкэнда - [ay-social](https://hub.docker.com/repository/docker/alexyakovlev90/ay-social)
  - [Скрипт](../Dockerfile) сборки, запаковки в докер, пуша в docker registry
2) Развернуть consul в вашей системе
  - [docker-compose](docker-compose.yml) для Consul и прочей инфраструктуры
  - [скрипт](start.sh) запуска и регистрации сервисов в Consul
  - после старта consul доступен на http://localhost:8500/


## Полезные ссылки 
- Service Discovery with Consul
  - https://cloud.spring.io/spring-cloud-consul/multi/multi_spring-cloud-consul-discovery.html
- Docker Compose + Consul + Spring Boot - https://habr.com/ru/post/429472/
- Consul: Service Discovery это просто - https://habr.com/ru/post/266139/
- Consul.io Часть 1 - https://habr.com/ru/post/278085/
