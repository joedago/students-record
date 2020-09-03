package com.joedago.studentsrecord.service;

import static com.joedago.studentsrecord.utils.ModelsGenerator.STUDENT_ID_1;
import static com.joedago.studentsrecord.utils.ModelsGenerator.STUDENT_ID_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.studentsrecord.model.PositionResponse;
import com.joedago.studentsrecord.model.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entity.Student;
import com.joedago.studentsrecord.persistence.repository.PositionRepository;
import com.joedago.studentsrecord.persistence.repository.StudentRepository;
import com.joedago.studentsrecord.service.impl.StudentServiceImpl;
import com.joedago.studentsrecord.utils.ModelsGenerator;

@SpringBootTest
class StudentServiceTest {

	@Mock
	private PositionRepository positionRepository;
	@Mock
	private StudentRepository studentRepository;
	@InjectMocks
	private StudentServiceImpl studentService;
	
	@BeforeEach
	public void setup() {
		final PositionResponse positionResponse = ModelsGenerator.generatePositionResponse();
		when(positionRepository.getPositionFromStudent(Mockito.any(Student.class))).thenReturn(positionResponse);
	}
	
	@Test
	void testSaveStudent() {
		when(studentRepository.findByStudentId(Mockito.anyInt())).thenReturn(null);
		when(studentRepository.findByEmail(Mockito.anyString())).thenReturn(null);
		final Student student = ModelsGenerator.generateValidStudent();
		studentService.saveStudent(student);
	}
	
	@Test
	void testSaveStudentExistentId() {
		final Student student = ModelsGenerator.generateValidStudent();
		when(studentRepository.findByStudentId(student.getStudentId())).thenReturn(student);
		try {
			studentService.saveStudent(student);
			fail();
		} catch(ResponseStatusException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
		}
	}
	
	@Test
	void testSaveStudentExistentEmail() {
		final Student student = ModelsGenerator.generateValidStudent();
		when(studentRepository.findByStudentId(student.getStudentId())).thenReturn(null);
		when(studentRepository.findByEmail(student.getEmail())).thenReturn(student);
		try {
			studentService.saveStudent(student);
			fail();
		} catch(ResponseStatusException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
		}
	}
	
	@Test
	void testCompareStutendsSimilarity() {
		final Student student1 = ModelsGenerator.generateValidStudent(STUDENT_ID_1);
		final Student student2 = ModelsGenerator.generateValidStudent(STUDENT_ID_2);
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(student1);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(student2);
		final StudentSimilarity similarity = studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
		assertNotNull(similarity);
		assertEquals(100, similarity.getSimilarityPercentage());
	}
	
	
	@Test
	void testStudent1DoesntExists() {
		Student student1 = ModelsGenerator.generateValidStudent(STUDENT_ID_1);
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(student1);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(null);
		try {
			studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
			fail();
		} catch(ResponseStatusException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
		}
	}
	
	@Test
	void testStudent2DoesntExists() {
		Student student2 = ModelsGenerator.generateValidStudent(STUDENT_ID_2);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(student2);
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(null);
		try {
			studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
			fail();
		} catch(ResponseStatusException ex) {
			assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
		}
	}
}
