package chapter2;

/**
 * @author XuYanXin
 * @program java_learning
 * @description 一个单例类懒加载的例子，但是这个类在多线程环境下存在竞态条件问题
 * @date 2020/9/27 1:44 下午
 */

public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        // 因为方法没有加锁，多线程环境下可能有多个线程到这一步判断，然后同时为 ture
        // 就会导致 ExpensiveObject 被初始化多次，如果预设场景只能初始化一次的话
        // 就会导致出现问题
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }
}

/**
 * 一个比较重量级的对象，可能初始化需要很多资源或者较长的时间
 */
class ExpensiveObject {

}
