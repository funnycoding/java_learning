package chapter2;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author XuYanXin
 * @program java_learning
 * @description 使用 AtomicLong 原子类保证递增操作原子性的 Servlet
 * @date 2020/9/27 1:57 下午
 */

public class CountingFactorizer extends GenericServlet implements Servlet {
    // 声明了一个原子类，当类中仅存在一个原子类时，操作必定是原子操作
    private final AtomicLong count = new AtomicLong(0);

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        // 重点在于对计数器的递增，这里的原子类底层使用 CAS 无锁结构，既保证了原子性，又保证了性能
        // 对比之前的 ++count 读取——修改——写入，三个动作并非一体，随时可能发生线程切换
        // 这里的  AtomicLong incrementAndGet 可以保证  读取——修改——写入 是一个完整的动作，不会被打断
        count.incrementAndGet();

        encodeIntoResponse(res, factors);
    }

    // ------ 下面是模拟的业务方法，没有实际意义 ------
    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return null;
    }

    BigInteger[] factor(BigInteger i) {
        return null;
    }
}
