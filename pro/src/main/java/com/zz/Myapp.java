package com.zz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by admin on 2017/6/18.
 */
@EnableAutoConfiguration
@ComponentScan
public class Myapp {
    public static void main(String[] args) {
        SpringApplication.run(Myapp.class,args);
    }
}
