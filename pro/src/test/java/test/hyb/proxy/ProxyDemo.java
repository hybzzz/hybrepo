package test.hyb.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by admin on 2017/12/11.
 */
public class ProxyDemo {
    public static Object getProxy(final Object target , final Advice advice){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                advice.beforeMethod(method);
                method.invoke(target,args);
                advice.afterMethod(method);
                return null;
            }
        });
    }
}
