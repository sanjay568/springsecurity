package com.springsecurity.learn.springsecurity.jwt;

import java.io.IOException;

import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtApplicationUserToken extends UsernamePasswordAuthenticationFilter{

	private  AuthenticationManager authManager;
	private JwtConfig config;
	
	@Autowired
	public JwtApplicationUserToken(AuthenticationManager authManager, JwtConfig config) {
		super();
		this.authManager = authManager;
		this.config = config;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		Authentication authen = null;
		System.out.println("Authentication attemptAuthentication >>>>>>>>>>>>>>>>....");
		try {
			AuthenicationRequest request2 = new ObjectMapper().readValue(request.getInputStream(), AuthenicationRequest.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(request2.getUsername().toString(),request2.getPassword().toString());
			authen = authManager.authenticate(authentication);	
		
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return authen;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		 String token = Jwts.builder()
        .setSubject("JWT AUTH")
        .claim("authorites",authResult.getAuthorities())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 3600000))
        .signWith(SignatureAlgorithm.HS512, config.getSecretKey())
        .compact();
		
		response.setHeader("Authorization", "Bearer "+token);
		//super.successfulAuthentication(request, response, chain, authResult);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Failure Occured >>>>>>>>>>>>>>>");
		response.getOutputStream().print("Failure to find the user in database");
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	
	
}
