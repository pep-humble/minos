package com.pep.actor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author liu.gang
 */
@Slf4j
public class SpringActorNode extends ActorNode {

    protected SpringActorNode(ActorMailbox nextMailBox) {
        super(nextMailBox);
    }

    @Override
    void doExecute(String message) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("actor-1 doExecute:{}", message);
    }
}
