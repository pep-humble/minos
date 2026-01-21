package com.pep.actor.normal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 冬天节点
 *
 * @author liu.gang
 */
@Slf4j
public class WinterNode extends INormalNode {

    public WinterNode(INormalNode nextNode) {
        super(nextNode);
    }

    @Override
    protected void doExecute(String message) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("node-4 doExecute:{}", message);
    }
}
