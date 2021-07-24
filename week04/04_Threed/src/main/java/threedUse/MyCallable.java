package threedUse;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Object> {
    @Override
    public Object call() throws Exception {
        System.out.println("---------开始执行线程---->" + Thread.currentThread().getName());
        for (int n = 0; n < 100000; n++) {

        }
        ReturnObj obj = new ReturnObj();
        obj.setValue1("Callable.Value1");
        obj.setValue2("Callable.Value2");
        System.out.println("---------执行完成线程---->" + Thread.currentThread().getName());
        return obj;
//        return 1;
    }
}
