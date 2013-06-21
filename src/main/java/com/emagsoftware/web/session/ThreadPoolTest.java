package com.emagsoftware.web.session;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * function description
 *
 * @author huzl
 * @version 1.0.0
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 10,
                                                      60L, TimeUnit.SECONDS,
                                                      new LinkedBlockingQueue<Runnable>());
        poolExecutor.getPoolSize();
        for(int i=0;i<15;i++){
        poolExecutor.execute(new TestThread(i+1));
        }
        System.out.println("------------------- begin -------------");
        new Thread(){
            @Override
            public void run() {
                while(true){
                    System.out.println("poolExecutor|" + poolExecutor.getPoolSize() + "|"+ poolExecutor.getTaskCount() + "|" + poolExecutor.getQueue().size());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }
            }
        }.start();
        System.out.println("------------------- end -------------");
    }
}

class TestThread extends Thread{
    int delay;

    TestThread(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            sleep(delay*1000);
            System.out.println("thread " + getId() + "|" + getName() + "|" + delay);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
