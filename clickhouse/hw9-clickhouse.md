# Сравнительное тестирование ClickHouse и MySQL

## Запуск ClickHouse и MySQL
- для проксирования и буферизации вставок в ClickHouse использован [KittenHouse](https://github.com/VKCOM/kittenhouse)
- для KittenHouse написан [dockerfile](./kittenhouse/Dockerfile)
- для запуска MySQL, ClickHouse и KittenHouse написан [docker-compose](docker-compose.yml)
- для ClickHouse при инициализации создаются таблицы, необходимые для KittenHouse. [Скрипт](./clickhouse-server/init/init_script.sql)
- [скрипт запуска](./start.sh)

## Сделать отчет по распределению пользователей по возрасту и полу на MySQL и Clickhouse
- создали таблицу пользователей в ClickHouse
```sql
CREATE TABLE default.user (
    id Int64,
    login String,
    first_name String,
    last_name String,
    age UInt8,
    sex UInt8,
    date Date DEFAULT toDate(time),
    time DateTime DEFAULT now()
)
ENGINE = MergeTree()
PARTITION BY (age,sex)
ORDER BY id;
``` 
- добавили в MySQL индекс по возрасту и полу
```sql
CREATE INDEX user_age_sex_idx ON user(age, sex);
```
- отчет по распределению пользователей по возрасту и полу
```sql
select age, sex, count(*) from user
group by age, sex
order by age, sex;
```

## Сравнить время построения отчета. Объяснить результат
- для тестирования построения отчета на MySQL и Clickhouse вставили 1 млн пользователей
- в ClickHouse отчет строится быстрее
- ClickHouse:
```sql
select age, sex, count(*) from user
         group by age, sex
         order by age, sex
[2020-08-09 18:23:34] 78 rows retrieved starting from 1 in 383 ms (execution: 320 ms, fetching: 63 ms)
```
- MySQL:
```sql
select age, sex, count(*) from user
      group by age, sex
      order by age, sex
[2020-06-21 12:53:48] 78 rows retrieved starting from 1 in 530 ms (execution: 470 ms, fetching: 60 ms)
```
- ClickHouse секционирован по (age,sex), в каждой секции количество пользователей уже посчитано в файле count.txt,
для отчета необходимо считать с диска count.txt в каждой партиции
- MySQL читает значения из оперативной памяти, без обращения на диск


