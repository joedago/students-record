package com.joedago.studentsrecord.controllers;

import static com.joedago.studentsrecord.controllers.ControllerConstants.STUDENT_COMPARE;
import static com.joedago.studentsrecord.controllers.ControllerConstants.STUDENT_ID_1;
import static com.joedago.studentsrecord.controllers.ControllerConstants.STUDENT_ID_2;
import static com.joedago.studentsrecord.controllers.ControllerConstants.STUDENT_URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joedago.studentsrecord.models.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entities.Student;
import com.joedago.studentsrecord.services.StudentService;

@RestController
@RequestMapping(STUDENT_URI)
public class StudentController {
	
	@Autowired
	StudentService studentService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveStudent(@Valid @RequestBody Student student) {
		studentService.saveStudent(student);
	}
	
	@GetMapping(STUDENT_COMPARE)
	public EntityModel<StudentSimilarity> compareStudents(
			@RequestParam(name = STUDENT_ID_1, required = true) Integer studentId1
			,@RequestParam(name = STUDENT_ID_2, required = true) Integer studentId2) {
		StudentSimilarity similarity = studentService.compareStudents(studentId1, studentId2);
		return EntityModel.of(similarity);
	}
}
