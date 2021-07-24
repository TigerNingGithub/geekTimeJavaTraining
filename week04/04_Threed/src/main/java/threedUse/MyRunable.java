package threedUse;

/**
 * 返回值，通过外部传递进来的对象赋值
 */
public class MyRunable implements Runnable {
    private ReturnObj objReturn;

    public MyRunable(ReturnObj objReturn) {
        this.objReturn = objReturn;
    }

    @Override
    public void run() {
        System.out.println("---------开始执行线程---->"+Thread.currentThread().getName());
        for (int n=0;n<100000;n++){

        }
        this.objReturn.setValue1("value1");
        this.objReturn.setValue2("value2");
        System.out.println("---------执行完成线程---->"+Thread.currentThread().getName());
    }
}
