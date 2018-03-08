package com.zz.utils.annotations;

import java.lang.annotation.*;

/**
 * Created by admin on 2017/7/6.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface SearchByLike {
    //模糊查询 左右模糊 模糊方向 left 左边模糊 %aaa
    String likeWay() default "both";

}
