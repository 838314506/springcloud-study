package com.lzz.springcloudstudy.threads.threadConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor getExecutor(){
        /**
         * new ThreadPoolTaskExecutor()方法源码使用LinkedBlockingQueue
         * 不指定容量，默认为Integer.MAX_VALUE，也就是无界队列
         * protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
         *         return (BlockingQueue)(queueCapacity > 0 ? new LinkedBlockingQueue(queueCapacity) : new SynchronousQueue());
         *     }
         */
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.initialize();
        return taskExecutor;
    }
}
