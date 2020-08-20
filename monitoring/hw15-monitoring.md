# Мониторинг


- zabbix - [docker-compose](./zabbix-mysql/docker-compose.yml)
- prometheus + grafana [docker-compose](./prometheus/docker-compose.yml)

- бизнес-метрики сервиса чатов по принципу RED
![red-app.png](./prometheus/screenshots/red-app.png)

- Spring Boot метрики приложения - Spring Boot 2.1 System Monitor
  - https://grafana.com/grafana/dashboards/11378
![sb-system-monitor.png](./prometheus/screenshots/sb-system-monitor.png)

- технические метрики сервера с сервисом чатов - Node Exporter Full
  - https://grafana.com/grafana/dashboards/1860
![node-exporter-graphs.png](./prometheus/screenshots/node-exporter-graphs.png)
  
  - список доступных технических метрик
![node-exporter-list.png](./prometheus/screenshots/node-exporter-list.png)  

- Технические метрики контейнеров Docker and system monitoring
  - https://grafana.com/grafana/dashboards/893
![docker.png](./prometheus/screenshots/docker.png)  


## Полезные ссылки

- Spring Boot Application - сетевой трафик дашборд - https://grafana.com/grafana/dashboards/9845
- Zabbix: мониторим всё подряд - https://habr.com/ru/post/485538/
- Zabbix Dockerfiles - https://github.com/zabbix/zabbix-docker

