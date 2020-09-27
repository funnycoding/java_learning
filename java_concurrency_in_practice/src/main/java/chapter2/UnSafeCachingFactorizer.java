package chapter2;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author XuYanXin
 * @program java_learning
 * @description 使用多个原子变量来保存状态的类，但是多个原子变量之间并不是原子的，所以存在竞态条件问题
 * @date 2020/9/27 2:07 下午
 */

public class UnSafeCachingFactorizer extends GenericServlet implements Servlet {
    private final AtomicReference<BigInteger> lastNumber
            = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors
            = new AtomicReference<>();

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(res, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            // ------ 下面是对两个原子变量的操作，但是这两个操作并不是一组原子的，所以存在线程切换导致数据异常的问题 ------
            lastNumber.set(i);
            lastFactors.set(factors);
            // ------  ------

            encodeIntoResponse(res, factors);
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
