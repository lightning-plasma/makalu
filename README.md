# Makalu

Spring Boot × gRPC kotlin Server Mock Application

## Usage

### BlockHound

spring profilesがprod / production 以外の場合はBlockHoundが起動します。

### grpc / netty debug log

すべての環境でgrpc / nettyのdebug logを出力します。無効化する場合は環境変数で上書きしてください

```shell
LOGGING_LEVEL_IO_GRPC=INFO
LOGGING_LEVEL_IO_NETTY=INFO
```
