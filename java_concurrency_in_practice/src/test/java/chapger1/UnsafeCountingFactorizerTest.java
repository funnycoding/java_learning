package chapger1;

import chapter1.UnsafeCountingFactorizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author XuYanXin
 * @program java_learning
 * @description
 * @date 2020/9/25 5:57 下午
 */

public class UnsafeCountingFactorizerTest extends Mockito {
    @Test
    public void testService() throws InterruptedException {
        UnsafeCountingFactorizer uncf = new UnsafeCountingFactorizer();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> uncf.service(request, response));
            thread.join();
            thread.start();

        }
        System.out.println(uncf.getCount());
        Assert.assertEquals(10, uncf.getCount());
    }
}
