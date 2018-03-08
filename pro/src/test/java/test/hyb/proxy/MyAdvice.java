package test.hyb.proxy;


import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by admin on 2017/12/11.
 */
public class MyAdvice implements Advice {
    long beginTime = 0;
    long endTime = 0;
    @Override
    public void beforeMethod(Method method) {
        beginTime=new Date().getTime();
        System.out.println("方法:"+method.getName()+"执行了");
    }

    @Override
    public void afterMethod(Method method) {
        endTime=new Date().getTime();
        System.out.println("方法:"+method.getName()+"共计执行了"+(endTime-beginTime)+"毫秒");
    }
}
