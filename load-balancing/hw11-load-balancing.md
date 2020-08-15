# Отказоустойчивость приложений

## Балансировка HTTP - nginx
- Поднять несколько инстансов приложения и обеспечить HTTP балансировку
  - [docker-compose](http-nginx/docker-compose.yml) с запуском бэкендов и nginx
  - скрипт запуска [start.sh](http-nginx/start.sh)
  - Конфигурация [nginx.conf](http-nginx/nginx/nginx.conf)
```
# number of unsuccessful attempts to communicate with the server
max_fails=number  ## default 1
# period of time the server will be considered unavailable
fail_timeout=time  ## default 10 seconds
```
  
  
## Балансировка TCP - haproxy
- Поднять несколько слейвов MySQL
  - [docker-compose](tcp-haproxy/docker-compose.yml) с запуском mysql кластера и необходимой инфры
  - скрипт запуска и настройки репликации [start.sh](tcp-haproxy/start.sh)
  - Конфигурация [haproxy.cfg](tcp-haproxy/haproxy/haproxy.cfg)
- Настроить TCP балансировку для MySQL
- Нагрузочные тесты с wrk
```shell script
wrk -t1 -c1 -d30s -s ../../index-load-test/wrk/search-test.lua --latency http://localhost:9292
```
- [Скрипт для генерации запросов на Lua](../index-load-test/wrk/search-test.lua)
- Результаты
```
- Нагрузка без выключения реплики
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.16ms    3.24ms  89.78ms   98.90%
    Req/Sec   335.07     56.41   420.00     73.67%

- Нагрузка с выключением реплики
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.34ms   22.97ms 367.06ms   98.89%
    Req/Sec   316.48     53.63   414.00     70.37%

```
- Логи HAProxy в момент выключения реплики - [haproxy.logs](./tcp-haproxy/haproxy.logs)


## Полезные ссылки
- Using nginx as HTTP load balancer 
  - http://nginx.org/en/docs/http/load_balancing.html
- Балансировщик HAProxy 
  - https://d2c.io/ru/article/how-to/haproxy-basic-ru
