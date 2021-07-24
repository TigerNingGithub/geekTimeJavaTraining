package threedUse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreedMain {

    /**
     * 2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这
     * 个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //第一种 threed+runnable+初始化对象 + while
        System.out.println("《《《《《《主线程开始threed01------------------------>");
        ReturnObj objReturn01 = new ReturnObj();
        Thread threed01 = new Thread(new MyRunable(objReturn01));
        threed01.setName("threed01");
        threed01.start();
        //等待
        while (objReturn01.getValue1() == null) {
            Thread.sleep(1000);
        }
        System.out.println("threed01>:" + objReturn01.toString());
        System.out.println("------------主线程完成threed01------------------------->");

        //第二种 threed+runnable+初始化对象 + theed.join()
        System.out.println("《《《《《《主线程开始threed02------------------------->");
        ReturnObj objReturn02 = new ReturnObj();
        Thread threed02 = new Thread(new MyRunable(objReturn02));
        threed02.setName("threed02");
        threed02.start();
        //等待 执行完成。
        threed02.join();
        System.out.println("threed02>:" + objReturn02.toString());
        System.out.println("------------主线程完成threed02------------------------->");

        //第三种 Thread+(FutureTask<Object> +Callable+泛型传值)+FutureTask.get()等待
        System.out.println("《《《《《《主线程开始FutureTask------------------------>");
        FutureTask<Object> task = new FutureTask<Object>(new MyCallable());
        Thread threed03 = new Thread(task);
        threed03.setName("threed03");
        threed03.start();
        try {
            System.out.println("FutureTask>:" + task.get().toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {

        }
        System.out.println("------------主线程完成FutureTask------------------------>");

        //第四种 线程池+ Executors + Callable+ Future.get()等待；
        System.out.println("《《《《《主线程开始Future------------------------>");
        ExecutorService executorService = Executors.newFixedThreadPool(2);//一般而言，cup核数*2
        Future<Object> task02 = executorService.submit(new MyCallable());
        try {
            System.out.println("Future>:" + task02.get().toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("------------主线程完成Future------------------------>");

        //第五种使用 锁= 一个返回值变量+锁+runnable，解锁完成后+在获取数据。  CountDownLatch用来阻塞主线程的，等子线程都干完事情后，在一起走。
        System.out.println("《《《《《主线程开始CountDownLatch------------------------>");
        ReturnObj objReturn05 = new ReturnObj();
        CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture<Void> future =
                CompletableFuture.runAsync(
                        new MyRunnableCountDownLatch(latch, objReturn05));
        latch.await();//等待所有锁解开
//        future.get();//等待某一锁解开
        System.out.println("CountDownLatch>:" + objReturn05.toString());
        System.out.println("------------主线程完成CountDownLatch------------------------->");

        //第六种使用 锁= CyclicBarrier  线程内部await（），所有线程等待，在一起执行。
        System.out.println("《《《《《主线程开始CyclicBarrier------------------------->");
        int ThreedNum = 3;
        CyclicBarrier barrier = new CyclicBarrier(ThreedNum);
        ReturnObj objReturn06 = new ReturnObj();
        List<CompletableFuture<Void>> listFuture = new ArrayList<>();
        for (int n = 0; n < ThreedNum; n++) {
            CompletableFuture<Void> future1 =
                    CompletableFuture.runAsync(
                            new MyRunnableCyclicBarrier(barrier, objReturn06));
            listFuture.add(future1);
        }
        for (CompletableFuture<Void> future1 : listFuture) {
            future1.get();//等待某一锁解开
        }
        System.out.println("CyclicBarrier>:" + objReturn06.toString());
        System.out.println("------------主线程完成CyclicBarrier------------------------->");

    }

}
