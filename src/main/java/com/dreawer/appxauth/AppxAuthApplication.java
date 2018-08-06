package com.dreawer.appxauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication注解等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan：
@SpringBootApplication
@EnableTransactionManagement
//@MapperScan(basePackages = {"com.dreawer.appxauth.persistence"})
public class AppxAuthApplication {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {

        applicationContext = SpringApplication.run(AppxAuthApplication.class, args);

    }
}
