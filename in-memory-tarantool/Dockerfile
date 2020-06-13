FROM centos:7

# установим необходимые пакеты для CentOS
RUN yum update -y && yum -y install sudo git ncurses-devel make cmake gcc gcc-c++ boost boost-devel wget unzip nano bzip2 mysql-devel mysql-lib
# клонируем пакет репликации Tarantool-MySQL
RUN git clone https://github.com/tarantool/mysql-tarantool-replication.git
# собрать репликатор с помощью cmake
RUN cd mysql-tarantool-replication && \
    git submodule update --init --recursive
RUN cd mysql-tarantool-replication && \
    cmake . && \
    make && \
    cp replicatord /usr/local/sbin/replicatord

COPY mysql-tarantool-replication/replicatord.service /etc/systemd/system
COPY mysql-tarantool-replication/replicatord.yml /usr/local/etc/replicatord.yml
COPY mysql-tarantool-replication/entrypoint.sh /

CMD ["/entrypoint.sh"]
