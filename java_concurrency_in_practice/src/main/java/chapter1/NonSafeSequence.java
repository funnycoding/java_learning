package chapter1;

/**
 * @author XuYanXin
 * @program java_learning
 * @description
 * @date 2020/9/26 11:29 下午
 */

public class NonSafeSequence {
    private int value;

    public  void addValue() throws InterruptedException {
        System.out.println("Thread:" + Thread.currentThread().getName()+"," + "value add 之前的值：" + value);
        value++;
        Thread.sleep(500);
        System.out.println("Thread:" + Thread.currentThread().getName()+"," + "value add 之后的值：" + value);
    }

    public int getValue() {
        return value;
    }
}
