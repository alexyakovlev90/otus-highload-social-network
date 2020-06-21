FROM golang:1.14.4

ENV CLICKHOUSE_HOST 127.0.0.1:8123
ENV SET_HOST -ch-addr=$CLICKHOUSE_HOST

RUN apt-get update && \
    apt-get install -y bash git make cmake gcc wget unzip nano bzip2

# установим kittenhouse
WORKDIR /go/src/kittenhouse
RUN go get github.com/vkcom/kittenhouse
RUN groupadd kitten && useradd -g kitten kitten \
    && mkdir /tmp/kittenhouse && chmod a+w /tmp/kittenhouse

CMD ["kittenhouse", "-ch-addr=clickhouse:8123"]
