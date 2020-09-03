package com.joedago.studentsrecord.utils;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.joedago.studentsrecord.persistence.entity.Student;
import com.joedago.studentsrecord.property.PositionStackProperties;
import com.joedago.studentsrecord.util.PositionUtils;

@SpringBootTest
class PositionUtilsTest {
	
	@Autowired
	private PositionStackProperties properties;
	
	@Test
	void testGetRequestUrl() {
		final Student student = ModelsGenerator.generateValidStudent();
		final URI uri = PositionUtils.getRequestUrl(student, properties);
		assertNotNull(uri);
	}

}
