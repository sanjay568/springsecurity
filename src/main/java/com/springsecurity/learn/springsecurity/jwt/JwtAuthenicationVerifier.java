package com.springsecurity.learn.springsecurity.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenicationVerifier extends OncePerRequestFilter  {

	@Autowired
	private JwtConfig config;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("OncePerRequestFilter called >>>>>>>>>>>>>>>>>>>>>>>>>>");
		String jwtToekn = null;
	    jwtToekn= request.getHeader("Authorization");
	    
	    if(StringUtils.isEmpty(jwtToekn) || !jwtToekn.startsWith("Bearer ")) {
	    	filterChain.doFilter(request, response);
	    	return;
	    }
	    
	    try {
	    	System.out.println(jwtToekn);
	    	jwtToekn = jwtToekn.replace("Bearer ","");
	    	System.out.println(jwtToekn);
	    	
	    	String username = Jwts.parser().setSigningKey(config.getSecretKey()).parseClaimsJws(jwtToekn).getBody().getSubject();
	    	System.out.println("username " +username);
	    	List<Map<String,String>> auths = (List<Map<String, String>>) Jwts.parser().setSigningKey(config.getSecretKey()).parseClaimsJws(jwtToekn).getBody().get("authorites");
	    	System.out.println(auths);
	    	Set<GrantedAuthority> aths= auths.stream().map(e -> new SimpleGrantedAuthority(e.get("authority"))).collect(Collectors.toSet());
	    	Authentication authentication = new UsernamePasswordAuthenticationToken(username,"password",aths
	    			);
	    	System.out.println("Authe check :"+authentication.isAuthenticated());
	    	SecurityContextHolder.getContext().setAuthentication(authentication);
	    	System.out.println("Authe check After :"+authentication.isAuthenticated());
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
			throw new IllegalStateException("token is not valid "+e);
		}
	    
	    filterChain.doFilter(request, response);
		
	}

}
