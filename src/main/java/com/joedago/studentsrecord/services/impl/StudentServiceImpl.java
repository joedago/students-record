package com.joedago.studentsrecord.services.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.studentsrecord.exceptions.ExceptionMessages;
import com.joedago.studentsrecord.models.DataResponse;
import com.joedago.studentsrecord.models.PositionResponse;
import com.joedago.studentsrecord.models.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entities.Student;
import com.joedago.studentsrecord.persistence.repositories.StudentRepository;
import com.joedago.studentsrecord.services.PositionService;
import com.joedago.studentsrecord.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	PositionService positionService;

	@Override
	public void saveStudent(Student student) {
		validateStudent(student);
		setPosition(student);
		setAgeAtRegistration(student);
		studentRepository.save(student);
	}
	
	@Override
	public StudentSimilarity compareStudents(Integer studentId1, Integer studentId2) {
		Student student1 = studentRepository.findByStudentId(studentId1);
		if(student1 == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ExceptionMessages.STUDENT_ID_DOES_NOT_EXISTS, studentId1));
		}
		Student student2 = studentRepository.findByStudentId(studentId2);
		if(student2 == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ExceptionMessages.STUDENT_ID_DOES_NOT_EXISTS, studentId2));
		}
		return getStudentSimilarity(student1, student2);
	}
	
	private StudentSimilarity getStudentSimilarity(Student student1, Student student2) {
		Integer percentage = 0;
		if(student1.getFirstName().equals(student2.getFirstName())) {
			percentage += 25;
		}
		if(student1.getLastName().equals(student2.getLastName())) {
			percentage += 25;
		}
		if(student1.getBirthDate().compareTo(student2.getBirthDate()) == 0) {
			percentage += 25;
		}
		if(student1.getState().equals(student2.getState())) {
			percentage += 25;
		}
		return getStudentSimilarity(student1, student2, percentage);
	}
	
	private StudentSimilarity getStudentSimilarity(Student student1, Student student2, Integer percentage) {
		StudentSimilarity similarity = new StudentSimilarity();
		similarity.setStudent1(student1);
		similarity.setStudent2(student2);
		similarity.setSimilarityPercentage(percentage);
		return similarity;
	}
	
	private void validateStudent(Student student) {
		if(student.getStudentId() != null) {
			Student existentStudent = studentRepository.findByStudentId(student.getStudentId());
			if(existentStudent != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.STUDENT_ID_EXISTS);
			}
		}
		Student existentEmailStudent = studentRepository.findByEmail(student.getEmail());
		if(existentEmailStudent != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.STUDENT_EMAIL_EXISTS);
		}
	}
	
	private void setPosition(Student student) {
		PositionResponse position = positionService.getPositionFromStudent(student);
		if(position != null && position.getData() != null && !position.getData().isEmpty()) {
			DataResponse data = position.getData().get(0);
			student.setLatitude(data.getLatitude());
			student.setLongitude(data.getLongitude());
		}
	}
	
	private void setAgeAtRegistration(Student student) {
		Calendar now = Calendar.getInstance();
		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(student.getBirthDate());
		Integer age = now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		student.setAgeAtRegistration(age);
	}

}
