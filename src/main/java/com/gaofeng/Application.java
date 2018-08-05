package com.gaofeng;

import com.gaofeng.usercenter.InitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: gaofeng
 * @Date: 2018-08-05
 * @Description:
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        InitConfig.init();
        SpringApplication.run(Application.class, args);
    }
}
