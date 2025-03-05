package com.ginkgooai.core.common.utils;

import com.ginkgooai.core.common.bean.ActivityType;
import com.ginkgooai.core.common.constant.MessageQueue;
import com.ginkgooai.core.common.message.ActivityLogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityLogger {
    
    private final RedissonClient redissonClient;

    public void log(String workspaceId, 
                   String projectId, 
                   String applicationId,
                   ActivityType activityType,
                   Map<String, Object> variables,
                   Map<String, Object> attachments,
                   String createdBy) {
        try {
            RQueue<ActivityLogMessage> queue = redissonClient.getQueue(MessageQueue.ACTIVITY_LOG_QUEUE);
            ActivityLogMessage message = ActivityLogMessage.builder()
                    .workspaceId(workspaceId)
                    .projectId(projectId)
                    .applicationId(applicationId)
                    .activityType(activityType.name())
                    .variables(variables)
                    .attachments(attachments)
                    .createdBy(createdBy)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            queue.offer(message);
            log.debug("Activity log message enqueued successfully for type: {}", activityType);
        } catch (Exception e) {
            log.error("Failed to enqueue activity log message for type: {}", activityType, e);
        }
    }
}