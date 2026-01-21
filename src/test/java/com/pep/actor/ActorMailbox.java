package com.pep.actor;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * actor信箱
 *
 * @author liu.gang
 */
public class ActorMailbox {

    /**
     * 执行器
     */
    private final ExecutorService executor;

    /**
     * actor处理器
     */
    private final ActorNode actor;

    /**
     * 信箱容器
     */
    private final ConcurrentLinkedQueue<String> msgQueue = new ConcurrentLinkedQueue<>();

    public ActorMailbox(ActorNode actor, String threadName) {
        this.actor = actor;
        executor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new CustomizableThreadFactory(threadName),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 消息投递到信箱
     *
     * @param msg 消息
     */
    public void enqueue(String msg) {
        msgQueue.add(msg);
        // 尝试处理消息
        tryProcessQueue(true);
    }

    /**
     * 尝试处理消息
     *
     * @param newMsg 是否新消息
     */
    private void tryProcessQueue(boolean newMsg) {
        // 只有是新消息或者队列不为空的时候, 才尝试调用处理方法, 可节省资源, 如果队列没有消息时, 无需while(true)
        if (newMsg || CollectionUtils.isNotEmpty(msgQueue)) {
            executor.execute(this::processMailbox);
        }
    }

    /**
     * 处理信箱的消息
     */
    private void processMailbox() {
        boolean noMoreElements = false;
        String msg = msgQueue.poll();
        if (msg != null) {
            actor.execute(msg);
        } else {
            noMoreElements = true;
        }
        if (noMoreElements) {
            executor.execute(() -> tryProcessQueue(false));
        } else {
            executor.execute(this::processMailbox);
        }
    }

}
