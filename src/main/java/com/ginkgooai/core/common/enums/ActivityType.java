package com.ginkgooai.core.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ActivityType {

	PROJECT_CREATED(
			"created a new project", "{user} created project '{project}'.",
		Arrays.asList("user", "project"),
		true,
		null
	),
	PROJECT_STATUS_CHANGE(
			"changed project status", "\"{project}\" status changed from {previousStatus} to {newStatus}.",
		Arrays.asList("project", "previousStatus", "newStatus"),
		true,
		null
	),
	ROLE_CREATED(
			"role created", "New role \"{roleName}\" created of \"{project}\".", Arrays.asList("roleName", "project"),
		true,
		null
	),
	ROLE_STATUS_UPDATE(
			"updated role status", "\"{roleName}\" status updated to {newStatus}.",
		Arrays.asList("roleName", "newStatus"),
		true,
		null
	),
	TALENT_IMPORTED("imported a talent", "Talent {talentName} is imported.", Arrays.asList("talentName"),
		true,
			"spreadsheet"),
	SUBMISSION_ADDED(
			"added a submission", "New submission added in {talentName}'s application for \"{roleName}\" of {project}.",
			Arrays.asList("talentName", "roleName", "project"),
		true,
		"video"
	),
	SUBMISSION_ADDED_TO_SHORTLIST(
			"added a submission to shortlist",
			"{talentName}'s for \"{roleName}\" of \"{project}\" has been shortlisted.",
		Arrays.asList("user", "talentName", "videoName"),
		true,
		"video"
	),
	TALENT_DIRECT_UPLOAD(
			"uploaded a submission", "{talentName} uploaded a submission for {roleName} of {project}.",
			Arrays.asList("talentName", "roleName", "project"),
		true,
		"video"
	),
	FEEDBACK_ADDED("feedback added",
			"New comment added in {talentName}'s application for \"{roleName}\" of \"{project}\".",
			Arrays.asList("talentName", "roleName", "project"),
		true,
		"text"
	);

	private final String description;
	private final String template;
	private final List<String> variables;
	private final boolean projectRelated;
	private final String attachmentType;

	ActivityType(String description, String template, List<String> variables, boolean projectRelated,
			String attachmentType) {
		this.description = description;
		this.template = template;
		this.variables = variables;
		this.projectRelated = projectRelated;
		this.attachmentType = attachmentType;
	}
}