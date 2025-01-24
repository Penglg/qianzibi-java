package com.qianzibi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.qianzibi"})
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan("com.qianzibi.mapper")
public class QianzibiApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(QianzibiApiApplication.class, args);
    }
}
