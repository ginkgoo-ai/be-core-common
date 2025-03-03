package com.ginkgooai.core.common.bean;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ActivityType {
    COMPANY_INFO_UPDATED(
            "{user} updated company profile",
            Arrays.asList("user"),
            false,
            "image"
    ),
    PROJECT_CREATED(
            "{user} created project '{project}'",
            Arrays.asList("user", "project"),
            true,
            null
    ),
    PROJECT_STATUS_CHANGE(
            "Project '{project}' status changed: {previousStatus} → {newStatus}",
            Arrays.asList("project", "previousStatus", "newStatus"),
            true,
            null
    ),
    ROLE_CREATED(
            "{user} created '{roleName}' in '{project}'",
            Arrays.asList("user", "roleName", "project"),
            true,
            null
    ),
    ROLE_STATUS_UPDATE(
            "'{roleName}' status updated to {newStatus}",
            Arrays.asList("roleName", "newStatus"),
            true,
            null
    ),
    TALENT_IMPORTED(
            "{count} talents imported from {source} • [Preview List]",
            Arrays.asList("count", "source", "time"),
            true,
            "spreadsheet"
    ),
    SUBMISSION_ADDED_TO_SHORTLIST(
            "{user} added {talentName}'s submission ({duration}) to shortlist • [Review Notes]",
            Arrays.asList("user", "talentName", "duration"),
            true,
            "video"
    ),
    BULK_INVITES_SENT(
            "{count} submission invitations sent to {agency} • [Notice Copy]",
            Arrays.asList("count", "agency"),
            true,
            "doc"
    ),
    SHORTLIST_EXPORTED(
            "Shortlist package ({size}) exported from '{project}' • [Watermarked Copy]",
            Arrays.asList("size", "project"),
            true,
            "archive"
    ),
    AUTO_EMAIL_TRIGGERED(
            "System sent {emailType} email to {recipientCount} recipients • [Template Preview]",
            Arrays.asList("emailType", "recipientCount"),
            false,
            "doc"
    ),
    MANUAL_EMAIL_SENT(
            "{user} sent {emailPurpose} email to {agency} • Delivery confirmed",
            Arrays.asList("user", "emailPurpose", "agency"),
            true,
            "links"
    ),
    EMAIL_TEMPLATE_CREATED(
            "{user} published new email template '{templateName}' • [Version History]",
            Arrays.asList("user", "templateName"),
            false,
            null
    ),
    TALENT_DIRECT_UPLOAD(
            "Self-submitted video ({duration}) received from {talentName} • Auto QC Passed",
            Arrays.asList("duration", "talentName"),
            true,
            "video"
    ),
    AGENCY_SUBMISSION_UPLOAD(
            "{agency} uploaded {talentName}'s audition ({resolution}p) • [Agency Notes]",
            Arrays.asList("agency", "talentName", "resolution"),
            true,
            "video"
    );

    private final String template;
    private final List<String> variables;
    private final boolean projectRelated;
    private final String attachmentType;

    ActivityType(String template, List<String> variables, boolean projectRelated, String attachmentType) {
        this.template = template;
        this.variables = variables;
        this.projectRelated = projectRelated;
        this.attachmentType = attachmentType;
    }
}