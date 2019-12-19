# 基于netty的RPC远程调用

> 模块说明
* rpc
* producer
* consumer
* common

> 流程说明
* 消费者连接到生产者（socket长连接）
* 消费者端生成cglib代理对象
* 拦截方法，发送请求报文
* 生产者解析报文
* 调用对应的Bean方法
* 响应结果

