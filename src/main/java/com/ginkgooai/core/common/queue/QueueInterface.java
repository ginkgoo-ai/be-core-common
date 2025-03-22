package com.ginkgooai.core.common.queue;

import org.redisson.api.listener.MessageListener;

import java.util.List;

public interface QueueInterface {

    <T extends QueueMessage> void send(String queueName, T message);


    void subscribe(String queueName, MessageListener listener);

    void shutdown();

    <T extends QueueMessage> List<T> getMessages(String queueName, int batchSize, Class<T> clazz);
}
