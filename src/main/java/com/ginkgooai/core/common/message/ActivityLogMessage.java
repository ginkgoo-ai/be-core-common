package com.ginkgooai.core.common.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ActivityLogMessage implements Serializable {
    private String workspaceId;
    private String projectId;
    private String applicationId;
    private String activityType;
    private String description;
    private Map<String, Object> context;
    private String createdBy;
    private LocalDateTime createdAt;
}
