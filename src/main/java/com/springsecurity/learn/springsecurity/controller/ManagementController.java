package com.springsecurity.learn.springsecurity.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.learn.springsecurity.dao.Student;

@RestController
@RequestMapping("management/api/v1/students")
public class ManagementController {
	
	private List<Student> students = Arrays.asList(
			  new Student(1, "Anna"),
			  new Student(2,"linda"),
			  new Student(3, "John")
			);
	
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('management:write','management:read')")
	public List<Student> displayAllStudents() {
		System.out.println("displayAllStudents()>>>>>>>>>>>>>>");
		return students;
	}
	
	
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('management:write')")
	public List<Student> createStudent(@RequestBody Student student) {
		System.out.println("createStudent()>>>>>>>>>>>>>>");
		return students;
	}
	
	@DeleteMapping("/{studdentId}")
	@PreAuthorize("hasAuthority('management:write')")
	public List<Student> deleteStudent(@PathVariable("studdentId") Integer studentId) {
		System.out.println("deleteStudent()>>>>>>>>>>>>>>");
		List<Student> newStudents = students.stream()
			    .filter(student -> student.getId()!=studentId)
			    .collect(Collectors.toList());
		students =newStudents;
		
		return students;
	}
	
	@PutMapping("/{studdentId}")
	@PreAuthorize("hasAuthority('management:write')")
	public List<Student> updateStudent(@PathVariable("studdentId") Integer studentId, @RequestBody Student student) {
		System.out.println("updateStudent()>>>>>>>>>>>>>>");
		return students;
	}

}
