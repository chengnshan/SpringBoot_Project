# Nacos

## 配置微服务配置中心文件

> 参考: [https://www.jianshu.com/p/bf4cf1a6a6ae](https://www.jianshu.com/p/bf4cf1a6a6ae)

在 Nacos Spring Cloud 中，数据集(Data Id) 的配置完整格式如下：
- ${prefix}-${spring.profile.active}.${file-extension}

prefix:就是配置的服务名，默认是你配置的，通俗的说就是服务注册时注册到服务中心的服务名的值：
```properties
spring:
  application:
    name: shop-coupon #服务名
```
spring.profile.active：是配置开发环境的值，一个程序不可能总是在开发环境，可能需要切换到测试环境，上线环境，他们的配置文件都是不同的，所以为了方便环境切换，我们配置不同的开发环境文档。比如在application.yml中有配置dev，就是开发环境：
```properties
spring:
  profiles:
    active: dev #表示开发环境
```
最后我们需要指定配置文件类型，默认是properties。我们可以自己指定文件类型，比如配置：
```properties
spring:
  cloud:
    nacos:
      config:
        file-extension: yaml #指定配置文件类型为yaml文件
```

