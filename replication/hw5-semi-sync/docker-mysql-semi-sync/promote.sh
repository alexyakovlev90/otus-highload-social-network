#!/bin/bash

# configure new master
# for semi-sync replication
master_plugin_cmd='INSTALL PLUGIN rpl_semi_sync_master SONAME "semisync_master.so";'
set_master_global_cmd='SET GLOBAL rpl_semi_sync_master_enabled = 1; SET GLOBAL rpl_semi_sync_master_wait_for_slave_count = 1;'
show_var_cmd='SHOW VARIABLES LIKE "rpl_semi_sync%";'
docker exec mysql_slave1 sh -c "export MYSQL_PWD=111; mysql -u root -e '$master_plugin_cmd' -e '$set_master_global_cmd' -e '$show_var_cmd'"

# Configuring new master node replication user and get the initial replication co-ordinates
grant_cmd='GRANT REPLICATION SLAVE ON *.* TO "mydb_slave_user"@"%" IDENTIFIED BY "mydb_slave_pwd"; FLUSH PRIVILEGES;'
status_cmd='SHOW MASTER STATUS;'
docker exec mysql_slave1 sh -c "export MYSQL_PWD=111; mysql -u root -e '$grant_cmd' -e '$status_cmd'"

# update slave2 to new master
docker-ip() {
    docker inspect --format '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "$@"
}

MS_STATUS=`docker exec mysql_slave1 sh -c 'export MYSQL_PWD=111; mysql -u root -e "SHOW MASTER STATUS"'`
CURRENT_LOG=`echo $MS_STATUS | awk '{print $6}'`
CURRENT_POS=`echo $MS_STATUS | awk '{print $7}'`
echo $MS_STATUS
echo $CURRENT_LOG
echo $CURRENT_POS

# stop slave io before update
stop_old_slave_thread="STOP SLAVE IO_THREAD;"
docker exec mysql_slave1 sh -c "export MYSQL_PWD=111; mysql -u root -e '$stop_old_slave_thread'"
docker exec mysql_slave2 sh -c "export MYSQL_PWD=111; mysql -u root -e '$stop_old_slave_thread'"

# update master config
start_slave_stmt="CHANGE MASTER TO MASTER_HOST='$(docker-ip mysql_slave1)',MASTER_USER='mydb_slave_user',MASTER_PASSWORD='mydb_slave_pwd',MASTER_LOG_FILE='$CURRENT_LOG',MASTER_LOG_POS=$CURRENT_POS; START SLAVE;"
start_slave_cmd='export MYSQL_PWD=111; mysql -u root -e "'
start_slave_cmd+="$start_slave_stmt"
start_slave_cmd+='"'
docker exec mysql_slave2 sh -c "$start_slave_cmd"

docker exec mysql_slave2 sh -c "export MYSQL_PWD=111; mysql -u root -e 'SHOW SLAVE STATUS \G'"
