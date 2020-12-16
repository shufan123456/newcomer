package com.tuhu.sf.newcomer.task.service.bootstrap;

import com.tuhu.sf.newcomer.task.common.entiy.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients
@EnableHystrix
@ComponentScan(value = {"com.tuhu.sf.newcomer.task"})
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
@EnableScheduling
@MapperScan("com.tuhu.sf.newcomer.task.dao.mapper")
@EnableTransactionManagement

public class StartMain {

    public static void main(String[] args) {
        SpringApplication.run(StartMain.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0L,0L);
    }
}
