package com.ginkgooai.core.common.message;

import com.ginkgooai.core.common.queue.QueueMessage;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ActivityLogMessage extends QueueMessage implements Serializable {
    private String workspaceId;
    private String projectId;
    private String applicationId;
    private String activityType;
    private String description;
    private Map<String, Object> variables;
    private Map<String, Object> attachments;
    private String createdBy;
    private LocalDateTime createdAt;
}
