# Социальная сеть

- Репа - https://hub.docker.com/repository/docker/alexyakovlev90/ay-social
- Docker образ - https://hub.docker.com/repository/docker/alexyakovlev90/ay-social
- Приложение в AWS - http://ec2-18-195-119-80.eu-central-1.compute.amazonaws.com:8080/


## Environmental vars used:
- DB_URL - full DB URL with DB name, otherwise H2 file DB used
- JDBC_DRIVER - JDBC driver. Supported drivers:
  - com.mysql.jdbc.Driver - for mySql
  - org.postgresql.Driver - for Postgres
  - org.h2.Driver - for H2 DB (default)
- DB_USERNAME - DB username
- DB_PASSWORD - DB password

## MySQL in Docker
```shell script
docker run --name=mysql \
  -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=ay_1 -p 3306:3306 \
  -d mysql/mysql-server:8.0.1

# login into mysql
docker logs mysql 2>&1 | grep GENERATED # check the automatically generated password of root user, copy it
docker exec -it mysql mysql -u root -p # parse and press the Enter key

ALTER USER 'root'@'localhost' IDENTIFIED BY '<password>';
create database ay_social;
GRANT ALL PRIVILEGES ON *.* TO 'ay'@'localhost' IDENTIFIED BY 'ay_pass';
```


## wrk - a HTTP benchmarking tool
- https://github.com/wg/wrk
```shell script

wrk -t1 -c1 -d5s -s ./load-test/wrk/search-test.lua --latency http://localhost:9090/swagger-ui.html
```

## tarantool
- https://www.tarantool.io/ru/doc/1.10/reference/reference_lua/box_space/
```shell script
# create space
box.schema.space.create('myspace', {if_not_exists=true})

# имя 'primary', тип дерево, уникальный. part - индекс по 1й колонке (целое число). и 2й колонке строке  
box.space.myspace:create_index('primary', {type="TREE", unique=true, parts={1, 'unsigned', 2, 'string'}, if_not_exists=true})
box.space.myspace:create_index('tindex', {type="TREE", parts={3, 'unsigned'}, unique=false})
box.space.myspace:create_index('hindex', {type="HASH", parts={1, 'unsigned'}})

space = box.schema.space.create('tester')
box.space.tester:create_index('primary', {type = 'hash', parts = {1, 'NUM'}})
box.schema.user.create('test', { password = 'test' })
box.schema.user.grant('test', 'execute,received,write', 'universe')
box.space.tester:format{{name='id',type='num'},{name='text',type='str'}}


dofile('/opt/tarantool/init.lua')
```
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
