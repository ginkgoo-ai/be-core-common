package com.ginkgooai.core.common.queue;

import lombok.Data;

@Data
public class QueueMessage {
    private String msgId;
    private Long timestamp;
}
