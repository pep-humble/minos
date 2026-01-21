package com.pep.actor;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 链
 *
 * @param rootMailbox 根节点
 * @param messageList 消息
 * @author liu.gang
 */
@Slf4j
public record ActorNodeChain(ActorMailbox rootMailbox, List<String> messageList) {

    public static void main(String[] args) throws InterruptedException {
        ActorMailbox rootMailbox = new ActorMailbox(
                new SpringActorNode(
                        new ActorMailbox(
                                new SummerActorNode(
                                        new ActorMailbox(
                                                new AutumnActorNode(
                                                        new ActorMailbox(
                                                                new WinterActorNode(null),
                                                                "actor-exec-4"
                                                        )
                                                ),
                                                "actor-exec-3"
                                        )),
                                "actor-exec-2"
                        )),
                "actor-exec-1"
        );
        // 5条数据, 每条链4个节点 每个节点耗时1s
        ActorNodeChain normalNodeChain = new ActorNodeChain(
                rootMailbox,
                Arrays.asList(
                        "data-1",
                        "data-2",
                        "data-3",
                        "data-4",
                        "data-5"
                )
        );
        normalNodeChain.execute();
        // 避免主线程中断
        TimeUnit.SECONDS.sleep(100);
    }

    public void execute() {
        long start = new Date().getTime();
        log.info("actorNodeChain execute start");
        messageList.forEach(rootMailbox::enqueue);
        log.info("actorNodeChain execute end");
        log.info("consuming time: {}s", (new Date().getTime() - start) / 1000);
    }
}
