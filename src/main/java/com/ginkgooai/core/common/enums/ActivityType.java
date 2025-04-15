package com.ginkgooai.core.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ActivityType {

	PROJECT_CREATED(
			"{user} created project '{project}'.",
		Arrays.asList("user", "project"),
		true,
		null
	),
	PROJECT_STATUS_CHANGE(
			"'{project}' status changed from {previousStatus} to {newStatus}.",
		Arrays.asList("project", "previousStatus", "newStatus"),
		true,
		null
	),
	ROLE_CREATED(
			"{user} created '{roleName}' in '{project}'.",
		Arrays.asList("user", "roleName", "project"),
		true,
		null
	),
	ROLE_STATUS_UPDATE(
			"'{roleName}' status updated to {newStatus}.",
		Arrays.asList("roleName", "newStatus"),
		true,
		null
	),
	TALENT_IMPORTED("Talent {talentName} is imported.", Arrays.asList("talentName"),
		true,
			"spreadsheet"),
	SUBMISSION_ADDED(
			"New submission added in {talentName}'s application for \"{roleName}\" of {project}.",
			Arrays.asList("talentName", "roleName", "project"),
		true,
		"video"
	),
	SUBMISSION_ADDED_TO_SHORTLIST(
			"{talentName}'s for \"{roleName}\" of \"{project}\" has benn shortlisted.",
		Arrays.asList("user", "talentName", "videoName"),
		true,
		"video"
	),
	TALENT_DIRECT_UPLOAD(
			"{talentName} uploaded a submission for {roleName} of {project}.",
			Arrays.asList("talentName", "roleName", "project"),
		true,
		"video"
	),
	FEEDBACK_ADDED("New comment added in {talentName}’s application for \"{roleName}\" of \"{project}\".",
			Arrays.asList("talentName", "roleName", "project"),
		true,
		"text"
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