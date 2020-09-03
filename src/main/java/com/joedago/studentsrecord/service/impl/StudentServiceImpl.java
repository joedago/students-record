package com.joedago.studentsrecord.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.studentsrecord.exception.ExceptionMessages;
import com.joedago.studentsrecord.model.DataResponse;
import com.joedago.studentsrecord.model.PositionResponse;
import com.joedago.studentsrecord.model.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entity.Student;
import com.joedago.studentsrecord.persistence.repository.PositionRepository;
import com.joedago.studentsrecord.persistence.repository.StudentRepository;
import com.joedago.studentsrecord.service.StudentService;
import com.joedago.studentsrecord.util.StudentUtils;

@Service
public class StudentServiceImpl implements StudentService {
	
	private final StudentRepository studentRepository;
	private final PositionRepository positionRepository;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, PositionRepository positionRepository) {
		this.studentRepository = studentRepository;
		this.positionRepository = positionRepository;
	}

	@Override
	public void saveStudent(Student student) {
		validateStudent(student);
		setPosition(student);
		StudentUtils.setAgeAtRegistration(student);
		studentRepository.save(student);
	}
	
	@Override
	public StudentSimilarity compareStudents(Integer studentId1, Integer studentId2) {
		final Student student1 = studentRepository.findByStudentId(studentId1);
		if(student1 == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ExceptionMessages.STUDENT_ID_DOES_NOT_EXISTS, studentId1));
		}
		final Student student2 = studentRepository.findByStudentId(studentId2);
		if(student2 == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ExceptionMessages.STUDENT_ID_DOES_NOT_EXISTS, studentId2));
		}
		return StudentUtils.getStudentSimilarity(student1, student2);
	}
	
	private void validateStudent(Student student) {
		if(student.getStudentId() != null) {
			final Student existentStudent = studentRepository.findByStudentId(student.getStudentId());
			if(existentStudent != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.STUDENT_ID_EXISTS);
			}
		}
		final Student existentEmailStudent = studentRepository.findByEmail(student.getEmail());
		if(existentEmailStudent != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.STUDENT_EMAIL_EXISTS);
		}
	}
	
	private void setPosition(Student student) {
		final PositionResponse position = positionRepository.getPositionFromStudent(student);
		if(position != null && position.getData() != null && !position.getData().isEmpty()) {
			final DataResponse data = position.getData().get(0);
			student.setLatitude(data.getLatitude());
			student.setLongitude(data.getLongitude());
		}
	}
}
