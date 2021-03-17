package com.lzz.springcloudstudy.threads.threadConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


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
        threadPool.setCorePoolSize(2);
        threadPool.setMaxPoolSize(4);
        threadPool.setKeepAliveSeconds(60);
        threadPool.setQueueCapacity(5);
        threadPool.setThreadNamePrefix("taskController-");

        threadPool.setTaskDecorator(new ContextDecorator());
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPool;
    }

    /**
     * Spring 中的 ThreadPoolTaskExecutor 有一个配置属性 TaskDecorator，
     * TaskDecorator 是一个回调接口，采用装饰器模式。装饰模式是动态的给一个对象添加一些额外的功能，
     * 就增加功能来说，装饰模式比生成子类更为灵活。
     * 因此 TaskDecorator 主要用于任务的调用时设置一些执行上下文，或者为任务执行提供一些监视/统计。
     */
    private class ContextDecorator implements TaskDecorator{

        @Override
        public Runnable decorate(Runnable runnable) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    runnable.run();
                }finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}
