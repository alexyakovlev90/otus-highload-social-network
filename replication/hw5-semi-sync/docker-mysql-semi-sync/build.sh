#!/bin/bash

docker-compose down
rm -rf ./master/data/*
rm -rf ./slave1/data/*
rm -rf ./slave2/data/*
docker-compose build
docker-compose up -d

# configure master
# wait to start
until docker exec mysql_master sh -c 'export MYSQL_PWD=111; mysql -u root -e ";"'
do
    echo "Waiting for mysql_master database connection..."
    sleep 4
done

# for semi-sync replication
master_plugin_cmd='INSTALL PLUGIN rpl_semi_sync_master SONAME "semisync_master.so";'
set_master_global_cmd='SET GLOBAL rpl_semi_sync_master_enabled = 1; SET GLOBAL rpl_semi_sync_master_wait_for_slave_count = 2;'
show_var_cmd='SHOW VARIABLES LIKE "rpl_semi_sync%";'
docker exec mysql_master sh -c "export MYSQL_PWD=111; mysql -u root -e '$master_plugin_cmd' -e '$set_master_global_cmd' -e '$show_var_cmd'"

# Configuring master node replication user and get the initial replication co-ordinates
grant_cmd='GRANT REPLICATION SLAVE ON *.* TO "mydb_slave_user"@"%" IDENTIFIED BY "mydb_slave_pwd"; FLUSH PRIVILEGES;'
status_cmd='SHOW MASTER STATUS;'
docker exec mysql_master sh -c "export MYSQL_PWD=111; mysql -u root -e '$grant_cmd' -e '$status_cmd'"

# Configuring slaves
# wait to start
until docker-compose exec mysql_slave1 sh -c 'export MYSQL_PWD=111; mysql -u root -e ";"'
do
    echo "Waiting for mysql_slave database connection..."
    sleep 4
done
until docker-compose exec mysql_slave2 sh -c 'export MYSQL_PWD=111; mysql -u root -e ";"'
do
    echo "Waiting for mysql_slave database connection..."
    sleep 4
done

# Slave nodes for semi-sync replication
slave_plugin_cmd='INSTALL PLUGIN rpl_semi_sync_slave SONAME "semisync_slave.so";'
set_slave_global_cmd='SET GLOBAL rpl_semi_sync_slave_enabled = 1;'
for N in 1 2
  do docker exec mysql_slave$N sh -c "export MYSQL_PWD=111; mysql -u root -e '$slave_plugin_cmd' -e '$set_slave_global_cmd' -e '$show_var_cmd'"
done

docker-ip() {
    docker inspect --format '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "$@"
}

MS_STATUS=`docker exec mysql_master sh -c 'export MYSQL_PWD=111; mysql -u root -e "SHOW MASTER STATUS"'`
CURRENT_LOG=`echo $MS_STATUS | awk '{print $6}'`
CURRENT_POS=`echo $MS_STATUS | awk '{print $7}'`

echo $MS_STATUS
echo $CURRENT_LOG
echo $CURRENT_POS


start_slave_stmt="CHANGE MASTER TO MASTER_HOST='$(docker-ip mysql_master)',MASTER_USER='mydb_slave_user',MASTER_PASSWORD='mydb_slave_pwd',MASTER_LOG_FILE='$CURRENT_LOG',MASTER_LOG_POS=$CURRENT_POS; START SLAVE;"
start_slave_cmd='export MYSQL_PWD=111; mysql -u root -e "'
start_slave_cmd+="$start_slave_stmt"
start_slave_cmd+='"'
for N in 1 2
  do
  docker exec mysql_slave$N sh -c "$start_slave_cmd"

done

#until docker-compose exec mysql_slave sh -c 'export MYSQL_PWD=111; mysql -u root -e ";"'
#do
#    echo "Waiting for mysql_slave database connection..."
#    sleep 4
#done


docker exec mysql_slave1 sh -c "export MYSQL_PWD=111; mysql -u root -e 'SHOW SLAVE STATUS \G'"
docker exec mysql_slave2 sh -c "export MYSQL_PWD=111; mysql -u root -e 'SHOW SLAVE STATUS \G'"

#create_db_cmd="create database ay_social;"
#create_user_cmd="GRANT ALL PRIVILEGES ON *.* TO 'ay'@'%' IDENTIFIED BY 'ay_pass';"
#
#docker exec mysql_slave sh -c "$create_db_cmd"
#docker exec mysql_slave sh -c "$create_user_cmd"
