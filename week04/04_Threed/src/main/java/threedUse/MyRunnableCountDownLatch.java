package threedUse;




import java.util.concurrent.CountDownLatch;

public class MyRunnableCountDownLatch implements Runnable {
    CountDownLatch latch;
    ReturnObj objReturn;

    public MyRunnableCountDownLatch(CountDownLatch latch, ReturnObj obj) {
        this.latch = latch;
        this.objReturn = obj;
    }



    @Override
    public void run() {

        System.out.println("---------开始执行线程---->"+Thread.currentThread().getName());
        for (int n=0;n<100000;n++){

        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            this.latch.countDown();//执行完成
        }
        this.objReturn.setValue1("value1");
        this.objReturn.setValue2("value2");
        System.out.println("---------执行完成线程---->"+Thread.currentThread().getName());
    }
}
