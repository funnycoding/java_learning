package chapger1;

import chapter1.SafeSequence;
import chapter2.UnsafeCountingFactorizer;
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
    public void unsafeCounterTest() throws InterruptedException {
        UnsafeCountingFactorizer uncf = new UnsafeCountingFactorizer();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> uncf.service(request, response));
            thread.join();
            thread.start();
        }
        Assert.assertEquals(10, uncf.getCount());
    }

    @Test
    public void testSafeSequence() throws InterruptedException {
        SafeSequence ss = new SafeSequence();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    ss.addValue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            thread.join();
        }
        Assert.assertEquals(10, ss.getValue());
    }
}
