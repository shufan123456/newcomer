package com.tuhu.sf.newcomer.task.service.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableSwagger2
@EnableFeignClients
@EnableHystrix
@ComponentScan(value = {"com.tuhu.sf.newcomer.task"})
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
@MapperScan("com.tuhu.sf.newcomer.task.dao.mapper")
@EnableScheduling
public class StartMain {

    public static void main(String[] args) {
        SpringApplication.run(StartMain.class, args);
    }

}
