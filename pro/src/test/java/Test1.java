import com.zz.demo.services.impl.SysInfoServiceImpl;
import org.junit.Test;
import test.hyb.UserService;
import test.hyb.UserServiceImpl;
import test.hyb.UserServiceProxy;
import test.hyb.cglib.CglibProxy;
import test.hyb.proxy.Animal;
import test.hyb.proxy.Dog;
import test.hyb.proxy.MyAdvice;
import test.hyb.proxy.ProxyDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/12/11.
 */
public class Test1 {
    @Test
    public void test1(){
        UserServiceProxy userServiceProxy = new UserServiceProxy(new UserServiceImpl());
        userServiceProxy.saveUser();
    }

    @Test
    public void test2(){
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserService userService = (UserService)Proxy.newProxyInstance(userServiceImpl.getClass().getClassLoader(),
                userServiceImpl.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        method.invoke(new UserServiceImpl(),args);
                        return null;
                    }
                });
        userService.saveUser();
    }
    @Test
    public void test3(){
        Animal animal = (Animal) ProxyDemo.getProxy(new Dog(),new MyAdvice());//动态创建对象
        //diy对象
        animal.eat();
    }
    @Test
    public void test4(){
        CglibProxy cglibProxy = new CglibProxy();
        Animal animal = (Animal) cglibProxy.getProxy(Dog.class);
        animal.eat();
    }
    @Test
    public void test5(){
        SysInfoServiceImpl s = new SysInfoServiceImpl();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("app_name","fafu");
        System.out.println(s.getInfo(map));

    }
}
