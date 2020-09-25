package chapter1;

/**
 * @author XuYanXin
 * @program java_learning
 * @description 线程安全的计数器类
 * @date 2020/9/25 6:50 下午
 */

public class SafeSequence {
    private int value;

    /**
     * 使用 synchronized Java的内置同步原语 —— 锁 就可以保证同一时间只有1个线程进入这个方法
     */
    public synchronized void addValue() {
        value++;
    }

    public int getValue() {
        return value;
    }
}
