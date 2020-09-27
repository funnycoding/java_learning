package chapter2;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author XuYanXin
 * @program java_learning
 * @description
 * @date 2020/9/27 2:13 下午
 */

public class SynchronizedFactorizer extends GenericServlet implements Servlet {

    // ------ 下面两个变量的线程安全性由唯一访问方法上的锁保证 ------
    private BigInteger lastNumber;
    private BigInteger[] lastFactors;


    /**
     * 这里使用 synchronized 内置锁保证这个方法只有1个线程可以调用，
     * 但是这样的会造成非常严重的串行化，原本为高并发场景设计的 Servlet 现在只能每次接受一个请求
     * 性能方面不可接受
     * @param req
     * @param res
     */
    @Override
    public synchronized void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber))
            encodeIntoResponse(res, lastFactors);
        else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
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
