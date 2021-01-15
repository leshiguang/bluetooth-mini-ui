package com.lifesense.android.health.service.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by qwerty
 * Create on 2020/12/7
 **/
public class TaskScheduler {
    private ExecutorService pool;

    private TaskScheduler() {
        Executors.newSingleThreadExecutor();
        ThreadFactory namedThreadFactory = buildThreadFactory();
        //Common Thread Pool
        pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    public static TaskScheduler getInstance() {
        return SingleHolder.getSingleton();
    }

    public void exectue(Runnable action) {
        pool.execute(action);
    }

    private static class SingleHolder {
        private static TaskScheduler taskScheduler = new TaskScheduler();

        private static TaskScheduler getSingleton() {
            return taskScheduler;
        }
    }

    private ThreadFactory buildThreadFactory() {
        return new NamedThreadFactory("LZHealth");
    }

    private class NamedThreadFactory implements ThreadFactory{

        private final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup threadGroup;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public  final String namePrefix;

        NamedThreadFactory(String name){
            SecurityManager s = System.getSecurityManager();
            threadGroup = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            if (null==name || "".equals(name.trim())){
                name = "pool";
            }
            namePrefix = name +"-"+
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(threadGroup, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
