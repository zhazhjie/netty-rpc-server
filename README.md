# 基于netty的RPC远程调用

> 模块说明
* rpc：远程调用核心模块
* producer：生产者服务端
* consumer：消费者客户端
* common：公共包

> 流程说明
* 基于socket长连接
* 消费者生成cglib代理对象
* 拦截方法，发送请求报文
* 报文格式：(int)length+(json)body
* 生产者解析报文
* 调用对应的Bean方法
* 响应结果

