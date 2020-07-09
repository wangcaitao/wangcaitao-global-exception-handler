# wangcaitao-global-exception-handler

spring boot 统一异常处理

## 使用

1. 添加依赖
    
    ```xml
    <dependency>
        <groupId>com.wangcaitao</groupId>
        <artifactId>wangcaitao-global-exception-handler</artifactId>
        <version>2.1.3</version>
    </dependency>
    ```

1. 配置文件
    
    ```yaml
    spring:
      mvc:
        throw-exception-if-no-handler-found: true
      resources:
        add-mappings: false
    ```

1. `@SpringBootApplication` 修改为 `@SpringBootApplication(scanBasePackages = {"com.wangcaitao.global", "com.xxx.xxx"})`, `com.xxx.xxx` 为启动类所在 `package`
