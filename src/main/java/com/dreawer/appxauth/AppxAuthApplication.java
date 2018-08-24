package com.dreawer.appxauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication注解等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan：
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.dreawer.appxauth.persistence"})
public class AppxAuthApplication {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {

        applicationContext = SpringApplication.run(AppxAuthApplication.class, args);

    }

    @Bean // 定义REST客户端，RestTemplate实例
    @LoadBalanced // 开启负载均衡的能力
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1500);// 设置超时
        requestFactory.setReadTimeout(2000);
        return new RestTemplate(requestFactory);
    }


}
