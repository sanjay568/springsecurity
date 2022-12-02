package com.springsecurity.learn.springsecurity.roles;

public enum AplicationSecurityPersmissions {

	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSES_READ("courses:read"),
	COURSES_WRITE("courses:write"),
	MANAGEMENT_READ("management:read"),
	MANAGEMENT_WRITE("management:write");

	private String persmission;
	
	AplicationSecurityPersmissions(String permission) {
		this.persmission = permission;
	}

	public String getPersmission() {
		return persmission;
	}
}
