package test.hyb.proxy;

/**
 * Created by admin on 2017/12/11.
 */
public class Dog implements Animal
{
    @Override
    public void eat() {
        System.out.println("eating..............");
    }

    @Override
    public void sleep() {
        System.out.println("sleeping............");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
