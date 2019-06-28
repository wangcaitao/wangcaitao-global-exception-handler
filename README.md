# wangcaitao-global-exception-handler

spring boot 统一异常处理

## 使用

1. 添加依赖
    ```xml
    <dependency>
        <groupId>cn.wangcaitao</groupId>
        <artifactId>wangcaitao-global-exception-handler</artifactId>
        <versiion>1.0.1</version>
    </dependency>
    ```
1. 配置
    ```yaml
    spring:
      mvc:
        throw-exception-if-no-handler-found: true
      resources:
        add-mappings: false
    ```