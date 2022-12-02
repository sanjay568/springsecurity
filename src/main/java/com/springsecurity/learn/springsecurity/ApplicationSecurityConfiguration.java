package com.springsecurity.learn.springsecurity;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.springsecurity.learn.springsecurity.auth.ApplicationUserService;
import com.springsecurity.learn.springsecurity.jwt.JwtApplicationUserToken;
import com.springsecurity.learn.springsecurity.jwt.JwtAuthenicationVerifier;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordConfig pConfig;
	
	@Autowired
	private ApplicationUserService aUService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .csrf().disable()
		    .addFilter(new JwtApplicationUserToken(authenticationManager(),pConfig.jwtConfig()))
		    .addFilterAfter(new JwtAuthenicationVerifier(),JwtApplicationUserToken.class)
		    .authorizeRequests()
		    .antMatchers("/","index","/js/*","/css/*").permitAll()
		    .anyRequest()
		    .authenticated();
		    /*.rememberMe()
		    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
		    .key("somethingverysecure")
		    .and()
		    .formLogin()
		    .loginPage("/login").permitAll()
		    .defaultSuccessUrl("/courses",true)
		    .failureUrl("/error")
		    .and()
		    .logout()
		    .logoutUrl("/logout")
		    .clearAuthentication(true)
		    .invalidateHttpSession(true)
		    .deleteCookies("JSESSIONID","rememeber-me")
		    .logoutSuccessUrl("/login");*/
			//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
		    //.httpBasic();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getDaoAuthenticationProvider());
		//super.configure(auth);
	}
	
	@Bean
	public DaoAuthenticationProvider getDaoAuthenticationProvider() {
		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();authenticationProvider.setPasswordEncoder(pConfig.getPasswordEncode());
		authenticationProvider.setUserDetailsService(aUService);
		return authenticationProvider;
	}
	
	/*
	 * @Override
	 * 
	 * @Bean protected UserDetailsService userDetailsService() {
	 * 
	 * 
	 * return new InMemoryUserDetailsManager(
	 * 
	 * User.builder() .username("test2")
	 * .password(pConfig.getPasswordEncode().encode("password"))
	 * //.roles(ApplicationSecurityRoles.ADMIN.name())
	 * .authorities("management:write","management:read") .build(), User.builder()
	 * .username("tom") .password(pConfig.getPasswordEncode().encode("password"))
	 * .authorities("management:read")
	 * //.roles(ApplicationSecurityRoles.ADMINTRAINEE.name()) .build() );
	 * 
	 * 
	 * }
	 */
}
