package chapter2;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author XuYanXin
 * @program java_learning
 * @description 存在线程安全问题的 Servlet ，service 中的 ++count 是非原子操作，在多线程环境下会出现竞态条件问题
 * @date 2020/9/28 1:53 上午
 */

public class UnSafeCountingFactorizer extends GenericServlet implements Servlet {
    // 可变变量 计数器，在多线程环境下存在 安全问题
    private long count = 0;

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        // ------- 下面两步是业务操作  -------
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        // 这里对计数器的修改是一个非原子操作，在多线程环境下会出现问题
        ++count;
        encodeIntoResponse(res, factors);
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * 因式分解方法，这里只做说明，固定返回7
     *
     * @param req
     * @return
     */
    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    /**
     * 将传入的参数包装为数组返回
     *
     * @param i
     * @return
     */
    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
