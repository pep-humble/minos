package com.pep.actor.normal;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 链
 *
 * @param rootNode    根节点
 * @param messageList 消息
 * @author liu.gang
 */
@Slf4j
public record NormalNodeChain(INormalNode rootNode, List<String> messageList) {

    public static void main(String[] args) {
        INormalNode rootNode = new SpringNode(
                new SummerNode(
                        new AutumnNode(
                                new WinterNode(null)
                        )
                )
        );
        // 5条数据, 每条链4个节点 每个节点耗时1s
        NormalNodeChain normalNodeChain = new NormalNodeChain(
                rootNode,
                Arrays.asList(
                        "data-1",
                        "data-2",
                        "data-3",
                        "data-4",
                        "data-5"
                )
        );
        normalNodeChain.execute();
    }

    public void execute() {
        long start = new Date().getTime();
        log.info("normalNodeChain execute start");
        messageList.forEach(rootNode::execute);
        log.info("normalNodeChain execute end");
        log.info("consuming time: {}s", (new Date().getTime() - start) / 1000);
    }
}
