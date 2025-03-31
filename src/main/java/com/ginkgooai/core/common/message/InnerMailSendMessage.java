package com.ginkgooai.core.common.message;


import com.ginkgooai.core.common.queue.QueueMessage;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class InnerMailSendMessage extends QueueMessage implements Serializable {

    private String emailTemplateId;
    private String emailTemplateType;
    private List<Receipt> receipts;


    @Data
    @Builder
    public static class Receipt {
        private String to;
        private Map<String, String> placeholders;
    }
}
