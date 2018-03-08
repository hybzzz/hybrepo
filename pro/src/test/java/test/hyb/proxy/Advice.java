package test.hyb.proxy;

import java.lang.reflect.Method;

/**
 * Created by admin on 2017/12/11.
 */
public interface Advice {
    void beforeMethod(Method method);
    void afterMethod(Method method);
}
