package com.zz.utils.annotations;

import java.lang.annotation.*;

/**
 * Created by admin on 2017/7/6.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JoinColumn {
    //实体类 表1 字段x 外键 表2 字段y
    //select from 表1 left join  表2 on 1.x=2.y
    String columnName() ;
    //left join  inner join right
    String joinWay() default "lfet";
    Class subEntity();

}
