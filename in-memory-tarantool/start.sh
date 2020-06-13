#!/bin/bash

docker-compose down
rm -rf ./mysql-master/data/*
rm -rf ./tarantool/data/*
docker-compose build
docker-compose up -d

until docker exec mysql_master sh -c 'export MYSQL_PWD=111; mysql -u root -e ";"'
do
    echo "Waiting for mysql_master database connection..."
    sleep 4
done

# MySQL configuration
#create_user_stmt='CREATE USER <username>@'<host>' IDENTIFIED BY '<password>';'
repl_client_stmt="GRANT REPLICATION CLIENT ON *.* TO 'mydb_slave_user'@'%' IDENTIFIED BY 'mydb_slave_pwd';"
repl_slave_stmt="GRANT REPLICATION SLAVE ON *.* TO 'mydb_slave_user'@'%' IDENTIFIED BY 'mydb_slave_pwd';"
select_stmt="GRANT SELECT ON *.* TO 'mydb_slave_user'@'%' IDENTIFIED BY 'mydb_slave_pwd';"
flush_stmt="FLUSH PRIVILEGES;"
repl_cmd='export MYSQL_PWD=111; mysql -u root -e "'
repl_cmd+="$repl_client_stmt"
repl_cmd+=" $repl_slave_stmt"
repl_cmd+=" $select_stmt"
repl_cmd+=" $flush_stmt"
repl_cmd+='"'
docker exec mysql_master sh -c "$repl_cmd"


