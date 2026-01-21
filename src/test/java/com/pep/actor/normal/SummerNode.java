package com.pep.actor.normal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 夏天节点
 *
 * @author liu.gang
 */
@Slf4j
public class SummerNode extends INormalNode {

    public SummerNode(INormalNode nextNode) {
        super(nextNode);
    }

    @Override
    protected void doExecute(String message) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("node-2 doExecute:{}", message);
    }
}
