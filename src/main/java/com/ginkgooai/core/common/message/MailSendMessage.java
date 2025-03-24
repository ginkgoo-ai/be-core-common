package com.ginkgooai.core.common.message;

import com.ginkgooai.core.common.queue.QueueMessage;
import com.sun.mail.imap.IMAPMessage;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MailSendMessage extends QueueMessage implements Serializable {

    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;


    public static MailSendMessage fromMessage(IMAPMessage message) throws MessagingException, IOException {
        return MailSendMessage.builder()
                .from(InternetAddress.toString(message.getFrom()))
                .to(InternetAddress.toString(message.getRecipients(Message.RecipientType.TO)))
                .cc(InternetAddress.toString(message.getRecipients(Message.RecipientType.CC)))
                .bcc(InternetAddress.toString(message.getRecipients(Message.RecipientType.BCC)))
                .subject(message.getSubject())
                .body(getBody(message)).build();

    }

    private static String getBody(Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            return getBody(multipart.getBodyPart(0));
        }
        return "";
    }

    public static String getBody(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/*")) {
            return part.getContent().toString();
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String body = getBody(bodyPart);
                if (!body.isEmpty()) {
                    return body;
                }
            }
        }
        return "";
    }
}
