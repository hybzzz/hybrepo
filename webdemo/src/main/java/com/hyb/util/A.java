package com.hyb.util;

import com.hyb.service.TestService;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by admin on 2018/4/2.
 */
public class A {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
                .getBeanFactory();
        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(MapperFactoryBean.class);
        // 设置属性userAcctDAO,此属性引用已经定义的bean:userAcctDAO
        beanDefinitionBuilder
                .addPropertyReference("sqlSessionFactory", "sqlSessionFactory").addPropertyValue("mapperInterface","com.hyb.dao.TestDao");

        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition("testDao",
                beanDefinitionBuilder.getRawBeanDefinition());

        applicationContext.getBean(SqlSessionFactoryBean.class);
    }
}
