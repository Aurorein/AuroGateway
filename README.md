当前项目完成：
#### API网关通信组件
1.基于Netty NIO实现TCP请求处理，三个InboundHandler分为Http协议解析，权限认证，结果封装
2.请求基于JWT+Shiro框架实现权限认证
3.采用Cglib动态代理与Dubbo RPC泛化调用API实现统一泛化调用接口
4.整体使用网络通信，Session会话，映射器和执行器架构完成通信组件
