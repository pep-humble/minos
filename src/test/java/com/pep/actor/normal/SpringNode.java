package com.pep.actor.normal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 春天节点
 *
 * @author liu.gang
 */
@Slf4j
public class SpringNode extends INormalNode {

    public SpringNode(INormalNode nextNode) {
        super(nextNode);
    }

    @Override
    protected void doExecute(String message) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("node-1 doExecute:{}", message);
    }
}
