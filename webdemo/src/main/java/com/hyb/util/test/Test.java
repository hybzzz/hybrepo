package com.hyb.util.test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by admin on 2018/4/10.
 */
public class Test {


    public static void main(String[] args) {
//        Comparator c = new Comparator<Person>() {
//            public int compare(Person p1, Person p2) {
//                int j = p1.getAge()-p2.getAge();
//                if(j!=0){
//                    return j ;
//                }
//                return p1.getName().compareTo(p2.getName());
//            }
//        };
//        Person[] ps = {new Person(1,"a"),
//                new Person(3,"ss"),
//                new Person(2,"xx"),
//                new Person(2,"bb")
//        };
//        for (Person p:ps) {
//            System.out.println(p);
//        }
//        Arrays.sort(ps,c);
//        for (Person p:ps) {
//            System.out.println(p);
//        }
            ShadowClone c1 = new ShadowClone();
            //对c1赋值
            c1.setA(100) ;
            c1.setB("clone1") ;
            c1.setC(new int[]{1000}) ;

            System.out.println("克隆前: c1.a="+c1.getA() );
            System.out.println("克隆前: c1.b="+c1.getB() );
            System.out.println("克隆前: c1.c[0]="+c1.getC()[0]);
            System.out.println("-----------") ;

            //克隆出对象c2,并对c2的属性A,B,C进行修改

            ShadowClone c2 = (ShadowClone) c1.clone();

            //对c2进行修改
            c2.setA(50) ;
            c2.setB("clone2");
            int []a = c2.getC() ;
            a[0]=500 ;
            c2.setC(a);

            System.out.println("克隆后: c1.a="+c1.getA() );
            System.out.println("克隆后: c1.b="+c1.getB() );
            System.out.println("克隆后: c1.c[0]="+c1.getC()[0]);
            System.out.println("---------------") ;

            System.out.println("克隆后: c2.a=" + c2.getA());
            System.out.println("克隆后: c2.b=" + c2.getB());
            System.out.println("克隆后: c2.c[0]=" + c2.getC()[0]);
    }
}
