package com.pep.actor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author liu.gang
 */
@Slf4j
public class WinterActorNode extends ActorNode {

    protected WinterActorNode(ActorMailbox nextMailBox) {
        super(nextMailBox);
    }

    @Override
    void doExecute(String message) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("actor-4 doExecute:{}", message);
    }
}
