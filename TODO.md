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
3. Выбрать 2 запроса для тестирования, изменить для них датасорс
4. Поднять с docker-compose мастер-слейв с mysql 5.7
  - https://github.com/vbabak/docker-mysql-master-slave
5. Протестировать нагрузку, записать в отчет
