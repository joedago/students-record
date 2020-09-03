package com.joedago.studentsrecord.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.studentsrecord.Application;
import com.joedago.studentsrecord.model.PositionResponse;
import com.joedago.studentsrecord.persistence.entity.Student;
import com.joedago.studentsrecord.persistence.repository.PositionRepository;

@SpringBootTest(classes = Application.class)
class PositionRepositoryTest {

	@Autowired
	private PositionRepository positionRepository;
	
	@Test
	void testGetPositionFromStudent() {
		final Student student = new Student();
		student.setStreetAddress1("1600 Pennsylvania Ave NW");
		student.setCity("Washington");
		student.setState("DC");
		final PositionResponse response = positionRepository.getPositionFromStudent(student);
		assertNotNull(response);
		assertNotNull(response.getData());
		assertFalse(response.getData().isEmpty());
	}
	
	@Test
	void testGetPositionWithNoAddress() {
		final Student student = new Student();
		try {
			positionRepository.getPositionFromStudent(student);
			fail("should throw an exception");
		} catch(ResponseStatusException ex) {
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
		}
	}
}
