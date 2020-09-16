# Understanding Hikari

HikariCP is a reliable, high-performance JDBC connection pool. It is much faster, lightweight and have better performance as compare to other connection pool API. Because of all these compelling reasons, HikariCP is now the default pool implementation in Spring Boot 2. In this article, we will have a closer look to configure Hikari with Spring Boot.

## Hikari Settings

```
# maximum number of milliseconds that a client will wait for a connection
spring.datasource.hikari.connection-timeout = 20000

# minimum number of idle connections maintained by HikariCP in a connection pool
spring.datasource.hikari.minimum-idle= 10 

# maximum pool size
spring.datasource.hikari.maximum-pool-size= 10

# maximum idle time for connection
spring.datasource.hikari.idle-timeout=10000

# maximum lifetime in milliseconds of a connection in the pool after it is closed.
spring.datasource.hikari.max-lifetime=1000

# default auto-commit behavior.
spring.datasource.hikari.auto-commit=true
```

## Data Source Configuration

If you like to create a custom data source for your Spring Boot Hikari, we can create a simple configuration class

```java
@Configuration
public class DataSourceConfiguration {

    @Bean(name="customDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource customDataSource() {
        return DataSourceBuilder.create().build();
    }
}
```

## Hikari Tutorial URL

https://www.javadevjournal.com/spring-boot/spring-boot-hikari/#:~:text=HikariCP%20Configurations&text=datasource.,wait%20for%20a%20connection%20spring.&text=minimum%2Didle%3D%2010%20%23minimum,in%20a%20connection%20pool%20spring.
