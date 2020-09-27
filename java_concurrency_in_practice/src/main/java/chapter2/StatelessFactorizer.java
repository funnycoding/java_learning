package chapter2;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author XuYanXin
 * @program java_learning
 * @description 一个无状态的 Servlet，线程安全
 * @date 2020/9/27 1:36 下午
 */

public class StatelessFactorizer extends GenericServlet implements Servlet {
    /**
     * 一个没有与其他类/本类状态相关的业务方法，这个方法是线程安全的，在并发环境下也可以正确执行
     *
     * @param req
     * @param res
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(res, factors);
    }

    // ------ 下面是模拟的业务方法，没有实际意义 ------
    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        // 这个变量是栈封闭的，随着方法的调用结束自动被回收
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
