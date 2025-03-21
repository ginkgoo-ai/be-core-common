package com.ginkgooai.core.common.queue;

import org.redisson.api.listener.MessageListener;

public interface QueueInterface {

    <T extends QueueMessage> void send(String queueName, T message);


    void subscribe(String queueName, MessageListener listener);

    void shutdown();
}
