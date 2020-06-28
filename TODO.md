#HW#1

~~1. Регистрация нового пользователя~~
~~2. Переход на страницы других пользователей~~
~~3. Переход на домашнюю страницу после авторизации~~
~~4. Добавить шифрование/проверку пароля~~
~~5. Возможность подружиться~~
~~6. Добавить кнопку домой~~
~~7. Привести в порядок авторизацию~~
~~8. Запаковка в докер~~
~~9. Пуш докер образа в docker registry~~
10. Развернуть MySQL в AWS
  - https://aws.amazon.com/ru/getting-started/tutorials/create-mysql-db/
~~11. Перевезти проект на MySQL~~
~~12. Деплой на AWS~~
  - https://aws.amazon.com/ru/getting-started/tutorials/launch-a-virtual-machine/
  
~~13. Сделать доступным swagger чз nginx на /api/swagger-ui.html~~


#HW#3
~~1. Вставить записи~~
~~2. Написать скрипт для wrk теста~~
~~3. Написать метод для тестирования (firstName LIKE ? and secondName LIKE ?)~~
~~4. Провести нагрузочные тесты с количеством одновременных запросов 1/10/100/1000.~~
~~5. Построить графики latency, throughput и сохранить их в отчет~~
~~6. Построить подходящий индекс.~~
~~7. Построить графики и сохранить их в отчет~~
~~8. Написать отчет~~

#HW#4
~~1. Поднять cAdvisor и prometheus для мониторинга контейнеров~~
  - https://prometheus.io/docs/guides/cadvisor/
~~2. Прикрутить графану и дашборд для отображения метрик~~
  - https://github.com/vegasbrianc/prometheus
  - его дашборды https://grafana.com/grafana/dashboards/893
  - Есть еще "полный фарш" https://github.com/stefanprodan/dockprom 
  - статья автора на Хабре https://habr.com/ru/company/southbridge/blog/314212/.
~~4. Поднять с docker-compose мастер-слейв с mysql 5.7~~
  - https://github.com/vbabak/docker-mysql-master-slave
~~5. Изменить код на использование 2х датасорсов~~
~~6. Выбрать 2 запроса для тестирования, изменить для них датасорс~~
~~7. Протестировать нагрузку, записать в отчет~~

#HW#6
1. Определить модель данных и ключ шардирования
  - Система личных сообщений (шардинг) - https://qna.habr.com/q/571256
  - опыт ВК - https://www.youtube.com/watch?v=UGbLNJiJmQo
2. Определить решение для реализации шардирования
  - Discord with Cassandra https://blog.discord.com/how-discord-stores-billions-of-messages-7fa6ec7ee4c7
  - https://www.mongodb.com/blog/post/schema-design-for-social-inboxes-in-mongodb
  
#HW#7
1. Скрипт запуска tarantool
2. Настроить реплицирование в tarantool (лучше всего версии 1.10)
  - https://github.com/tarantool/mysql-tarantool-replication 
3. Подключение tarantool к приложению
4. Выбрать любой запрос и переписать его на lua-процедуру на tarantool

5. Выбрать запрос для нагрузочного тестирования (wrk скрипт)


#HW#9
1. Написать docker-compose для click-house и mysql
  - https://stackoverflow.com/questions/52198099/creating-db-and-tables-in-a-dockerized-clickhouse-instance-from-docker-compose-f
2. Настроить репликацию
  - https://mydbops.wordpress.com/2020/02/21/3-step-migration-of-mysql-data-to-clickhouse-for-faster-analytics/
  - https://www.altinity.com/blog/2018/6/30/realtime-mysql-clickhouse-replication-in-practice
3. Или использовать буффер
  - https://github.com/VKCOM/kittenhouse

#HW#8
~~1. Поднять redis~~
~~2. Поднять rabbit~~ 
~~3. Модель данных хранения сообщений~~
~~4. Нарисовать схему работы~~
~~5. Реализовать PUB/SUB с Rabbit~~
6. Прикрутить запись и обновление в Redis
6. Реализовать фронт
