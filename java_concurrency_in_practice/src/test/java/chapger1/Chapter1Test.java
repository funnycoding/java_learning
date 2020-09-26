package chapger1;

import chapter1.NonSafeSequence;
import chapter1.SafeSequence;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author XuYanXin
 * @program java_learning
 * @description
 * @date 2020/9/26 11:29 下午
 */

public class Chapter1Test {
    @Test
    public void testUnsafeSequence() throws InterruptedException {
        NonSafeSequence nonSafeSequence = new NonSafeSequence();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    nonSafeSequence.addValue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        Assert.assertEquals(10, nonSafeSequence.getValue());
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
