package com.springsecurity.learn.springsecurity.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ApplicationUserDao  {
	
	Optional<ApplicationUser> getApplicationUserByUsername(String s) throws UsernameNotFoundException;

}
