package threedUse;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class MyRunnableCyclicBarrier implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private ReturnObj objReturn;

    public MyRunnableCyclicBarrier(CyclicBarrier cyclicBarrier, ReturnObj obj) {
        this.cyclicBarrier = cyclicBarrier;
        this.objReturn = obj;
    }

    @Override
    public void run() {
        System.out.println("---------开始执行线程---->"+Thread.currentThread().getName());

        Integer millis = new Random().nextInt(10000);
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
            this.cyclicBarrier.await(); // 线程阻塞
            System.out.println("开吃:" +Thread.currentThread().getName());
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }finally {

        }
        this.objReturn.setValue1("value1");
        this.objReturn.setValue2("value2");
        System.out.println("---------执行完成线程---->"+Thread.currentThread().getName());
    }
}
