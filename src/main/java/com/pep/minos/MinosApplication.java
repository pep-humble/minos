package com.pep.minos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务启动入口
 *
 * @author gang.liu
 */
@SpringBootApplication
public class MinosApplication {

    /**
     * 启动方法
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(MinosApplication.class, args);
    }
}
