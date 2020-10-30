import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: fate-core
 * @Package: PACKAGE_NAME
 * @ClassName: ReentrantLockTest
 * @Author: LJP
 * @Description:
 * @Date: 2020/4/18 18:23
 * @Version: 1.0
 */
public class ReentrantLockTest {

    Lock reentrantLock = new ReentrantLock(true);


    @Test
    public void test() {

        for (int i = 0; i < 10; i++) {

            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    logic();
                }
            });
            t1.start();
        }
    }

    private void logic() {
        System.out.println("111111");
        boolean tryLock = reentrantLock.tryLock();
        if (tryLock) {
            try {
                System.out.println("222222");
            } catch (Exception e) {

            } finally {
                reentrantLock.unlock();
            }
        }
    }
}
