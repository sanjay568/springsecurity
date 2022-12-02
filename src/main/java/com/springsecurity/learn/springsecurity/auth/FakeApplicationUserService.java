package com.springsecurity.learn.springsecurity.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.springsecurity.learn.springsecurity.roles.ApplicationSecurityRoles;

@Repository("fake")
public class FakeApplicationUserService implements ApplicationUserDao {

	private PasswordEncoder encoder;
	
	@Autowired
	public FakeApplicationUserService(PasswordEncoder encoder) {
		super();
		System.out.println("Password Encoder >>>>>>>>>>>>>>> ");
		System.out.println(encoder);
		this.encoder = encoder;
	}

	@Override
	public Optional<ApplicationUser> getApplicationUserByUsername(String s) throws UsernameNotFoundException {
		System.out.println("User    FOund >>>>>>>>>>>>>>>>>"+s);
		return getApplicatiionUsers()
					.stream()
					.filter((e) -> e.getUsername().equals(s))
					.findFirst();
	}
	
	private List<ApplicationUser> getApplicatiionUsers() {
		
		List<GrantedAuthority> mdmin = Lists.newArrayList(new SimpleGrantedAuthority("management:read"),new SimpleGrantedAuthority("management:write"));
		List<GrantedAuthority> mstudent = Lists.newArrayList(new SimpleGrantedAuthority("management:read"));
		List<ApplicationUser> allUsers = Lists.newArrayList(
				new ApplicationUser(mdmin, encoder.encode("password") , "test2", true, true, true, true),
				new ApplicationUser(mstudent, encoder.encode("password") , "tom", true, true, true, true)
				
				);
		
		return allUsers;
				
	}

}
