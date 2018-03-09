package com.hyb.activemq.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Destination;

/**
 * Created by admin on 2017/12/26.
 */
public class Producer {

    public void send(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:activemq.xml");
        Destination destination = (Destination) context.getBean("destination");

    }

    public static void main(String[] args) {

    }
}
