package com.springsecurity.learn.springsecurity.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.learn.springsecurity.dao.Student;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	
	private final List<Student> students = Arrays.asList(
			  new Student(1, "Anna"),
			  new Student(2,"linda"),
			  new Student(3, "John")
			);
	
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable Integer id) {
		return students.stream().filter(student -> student.getId().equals(id))
				                .findFirst()
				                .orElseThrow(() -> new IllegalStateException("Student not found with this id : "+ id));
	}

}
