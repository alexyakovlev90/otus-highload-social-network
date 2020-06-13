# Репликация из MySQL в tarantool

## Запуск tarantool и MySQL
- Запуск и инициализация tarantool и MySQL 
  - для запуска MySQL и tarantool написан [docker-compose](docker-compose.yml)
  - [настройки MySQL](./mysql-master/conf/mysql.conf.cnf)
  - [настройки tarantool](./tarantool/conf/config.yml)
  - инициализация настроек репликации MySQL написан [скрипт](start.sh)
  - Для инициализации таблиц и индексов БД необходимо запустить приложение
  - Для инициализации спейса с индексами в tarantool необходимо запустить [lua скрипт](./tarantool/conf/init.lua)
  - Список команд:
```shell script
./start.sh
docker exec -it tarantool console
# Инициализация спейса, индекса, функции поиска
dofile('/opt/tarantool/init.lua')
start()
```


## Репликатор
- Для сборки образа c репликатором написан [докерфайл](Dockerfile)
- Репликатор будет работать в виде демона systemd под названием replicatord, его служебный файл systemd
  - Обновленная конфигурация сервиса репликатора [replicatord.service](./mysql-tarantool-replication/replicatord.service)
- Конфигурация репликации [replicatord.yml](./mysql-tarantool-replication/replicatord.yml)
```shell script
docker build -t replicator .
docker run -d -it --privileged replicator
```


## Нагрузочное тестирование
- Для тестирования выбран запрос поиска пользователей по префиксу
  - Для запроса написана lua-процедура на tarantool - [lua скрипт](./tarantool/conf/init.lua)
- Для теста сгенерировали 100 тыс тестовых пользователей
- Нагрузочные тесты с wrk
- Запросы в MySQL
```shell script
wrk -t8 -c400 -d120s -s ./wrk/search-mysql.lua --latency http://localhost:9292/
```
```
Running 2m test @ http://localhost:9292/
  8 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   163.78ms   99.07ms   1.30s    95.16%
    Req/Sec   327.55     66.61   540.00     76.31%
  Latency Distribution
     50%  147.72ms
     75%  158.50ms
     90%  197.88ms
     99%  788.15ms
  307042 requests in 2.00m, 115.18MB read
  Socket errors: connect 0, read 322, write 0, timeout 0
Requests/sec:   2556.69
Transfer/sec:      0.96MB
```
- Запросы в tarantool
```shell script
wrk -t8 -c400 -d120s -s ./wrk/search-tarantool.lua --latency http://localhost:9292/
```
```
Running 2m test @ http://localhost:9292/
  8 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    48.06ms   69.50ms   1.46s    97.73%
    Req/Sec     1.26k   306.04     2.39k    74.28%
  Latency Distribution
     50%   36.62ms
     75%   42.42ms
     90%   56.92ms
     99%  422.76ms
  1183478 requests in 2.00m, 439.95MB read
  Socket errors: connect 0, read 339, write 0, timeout 0
Requests/sec:   9854.18
Transfer/sec:      3.66MB
```
-- При одинаковых условиях нагрузки пропускная способность 1го инстанца tarantool оказалась в 3.5 раз больше


## Полезные ссылки
- Репликация MySQL в tarantool Centos
  - https://github.com/tarantool/mysql-tarantool-replication
  - https://www.tarantool.io/ru/learn/improving-mysql/
- tarantool on Docker Hub
  - https://hub.docker.com/r/tarantool/tarantool/
- Команды в tarantool
  - https://www.tarantool.io/en/doc/1.10/reference/reference_lua/box_space/
- Нагрузка с wrk
  - https://github.com/wg/wrk
