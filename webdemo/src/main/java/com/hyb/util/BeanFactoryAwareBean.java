package com.hyb.util;

import com.hyb.anno.Mapper;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.io.IOException;

/**
 * Created by admin on 2018/4/3.
 */
public class BeanFactoryAwareBean implements BeanFactoryAware{
//        ,ApplicationListener<ContextRefreshedEvent> {
    private DefaultListableBeanFactory beanFactory;
    private String scanScope;


    public void setScanScope(String scanScope) {
        this.scanScope = scanScope;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory.........................");
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        try {
            doScan(scanScope);
        } catch (Exception e) {
            System.out.println("动态注册bean异常");
            e.printStackTrace();
        }
    }

//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        System.out.println("ContextRefreshed...................");
////        try {
////            doScan(scanScope);
////        } catch (Exception e) {
////            System.out.println("动态注册bean异常");
////            e.printStackTrace();
////        }
//    }
    public void regMapper(String javapath,String beanname){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MapperFactoryBean.class);
        // 设置属性userAcctDAO,此属性引用已经定义的bean:userAcctDAO
        beanDefinitionBuilder
                .addPropertyReference("sqlSessionFactory", "sqlSessionFactory").addPropertyValue("mapperInterface",javapath);

        // 注册bean
        beanFactory.registerBeanDefinition(beanname,
                beanDefinitionBuilder.getRawBeanDefinition());
    }
    public String resolvePath(String basepath){
        String path = basepath.replace(".","/");
        path = path+"/**/*.class";
        return path;
    }
    public void doScan(String scanScope) throws IOException {
        PathMatchingResourcePatternResolver p = new PathMatchingResourcePatternResolver();
        Resource[] resources = p.getResources(resolvePath(scanScope));
        for (Resource r: resources) {
            String path = r.getFile().getAbsolutePath();
            String res = resolveClassPath(scanScope,path);
            Class<?> clazz = null;
            try {
                clazz = Class.forName(res);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Mapper mapper = clazz.getAnnotation(Mapper.class);
            if(mapper!=null){//如果有mapper注解
                String beanName = getNameFirstLow(clazz.getSimpleName());
                regMapper(res,beanName);
            }
        }
    }
    public String resolveClassPath(String basepath , String path){
        String temp = basepath + path.split(basepath)[1].replace(".class","");
        if(System.getProperties().getProperty("os.name").indexOf("Windows")>-1){
            temp = temp.replace("\\",".");
        }else{
            temp = temp.replace("/",".");
        }
        return temp;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(Class.forName("com.hyb.dao.TestDao").getSimpleName());
        Mapper annotation = Class.forName("com.hyb.dao.TestDao").getAnnotation(Mapper.class);
        System.out.println(annotation);
    }
    public String getNameFirstLow(String name){
        return name.substring(0,1).toLowerCase()+name.substring(1);
    }
}