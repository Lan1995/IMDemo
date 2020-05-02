Netty版本的IM
### COMMON
common模块包含客户端,服务器端通用的代码逻辑
1. 通用的ChanelHandler
- 拆包粘包处理器
- 编解码器
- 心跳
2. 自定义协议相关的抽象类以及对应的工具类
自定义协议如下
|魔术(4 byte) | 协议版本(1 byte)|序列化算法类型(1 byte) | 指令(1 byte)  | 数据长度 (4 byte)|data|
- 协议版本
- 自定义序列化

