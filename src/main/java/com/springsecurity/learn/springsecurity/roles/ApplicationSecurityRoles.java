package com.springsecurity.learn.springsecurity.roles;

import java.util.Set;
import com.google.common.collect.Sets;

public enum ApplicationSecurityRoles {

	STUDENT(Sets.newHashSet(AplicationSecurityPersmissions.STUDENT_READ
			               ,AplicationSecurityPersmissions.STUDENT_WRITE
			               ,AplicationSecurityPersmissions.COURSES_READ)),
	
	ADMIN(Sets.newHashSet(AplicationSecurityPersmissions.STUDENT_READ
            ,AplicationSecurityPersmissions.STUDENT_WRITE
            ,AplicationSecurityPersmissions.COURSES_READ
            ,AplicationSecurityPersmissions.COURSES_WRITE,
            AplicationSecurityPersmissions.MANAGEMENT_READ,
            AplicationSecurityPersmissions.MANAGEMENT_WRITE
			)),
	
	ADMINTRAINEE(Sets.newHashSet(AplicationSecurityPersmissions.MANAGEMENT_READ));
            
	private Set<AplicationSecurityPersmissions> permissions;

	private ApplicationSecurityRoles(Set<AplicationSecurityPersmissions> permissions) {
		this.permissions = permissions;
	}

	public Set<AplicationSecurityPersmissions> getPermissions() {
		return permissions;
	}
	
}
