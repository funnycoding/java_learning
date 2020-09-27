package chapter2;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author XuYanXin
 * @program java_learning
 * @description 线程安全的类，并且使用了细粒度锁，只在必要的地方加锁
 * @date 2020/9/27 11:58 下午
 */

public class CachedFactorizer extends GenericServlet implements Servlet {
    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    private long hits;
    private long cacheHits;


    // 访问 hits 和 cacheHits 的方法都使用了内置锁，保证了变量的线程安全性
    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    // 这里并没有直接在方法上使用 synchronized，因为会导致整个方法的串行
    // 而是在方法内部找到需要使用锁的地方使用，这种锁叫做细粒度
    // 下面的方法只在对类中变量做修改的地方使用锁，其他地方没有锁
    // 所以 service 方法可以同时接受很多线程的请求，但是在被锁保护的代码块中只有一个线程可以进入
    // 其他没有获取锁的线程需要在等待队列中阻塞，等待执行完成的线程唤醒
    @Override
    public void service(ServletRequest req, ServletResponse res) {
        // 下面2个不涉及对类中变量的存取，所以不需要加锁
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        // 下面的代码块使用当前对象实例 this 作为锁，与上面的方法中的锁保证是同一个对象
        // 下面这部分需要对 hits 和 cacheHits 递增，所以需要使用锁来保证原子性
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }

        if (factors == null) {
            factors = factor(i);
            // 下面是对因式分解数据的缓存
            // 也需要对类中的原子类做修改，所以需要保证这两个操作是一组原子操作
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
    }

    // ------ 下面是模拟的业务方法，没有实际意义 ------
    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }

}
