package com.lzz.springcloudstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringcloudStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudStudyApplication.class, args);
    }

}
