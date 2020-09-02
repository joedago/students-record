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

import com.joedago.studentsrecord.models.PositionResponse;
import com.joedago.studentsrecord.models.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entities.Student;
import com.joedago.studentsrecord.persistence.repositories.StudentRepository;
import com.joedago.studentsrecord.services.PositionService;
import com.joedago.studentsrecord.services.impl.StudentServiceImpl;
import com.joedago.studentsrecord.utils.ModelsGenerator;

@SpringBootTest
class StudentServiceTest {

	@Mock
	PositionService positionService;
	@Mock
	StudentRepository studentRepository;
	@InjectMocks
	StudentServiceImpl studentService;
	
	@BeforeEach
	public void setup() {
		PositionResponse positionResponse = ModelsGenerator.generatePositionResponse();
		when(positionService.getPositionFromStudent(Mockito.any(Student.class))).thenReturn(positionResponse);
	}
	
	@Test
	void testSaveStudent() {
		when(studentRepository.findByStudentId(Mockito.anyInt())).thenReturn(null);
		when(studentRepository.findByEmail(Mockito.anyString())).thenReturn(null);
		Student student = ModelsGenerator.generateValidStudent();
		studentService.saveStudent(student);
	}
	
	@Test
	void testSaveStudentExistentId() {
		Student student = ModelsGenerator.generateValidStudent();
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
		Student student = ModelsGenerator.generateValidStudent();
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
		Student student1 = ModelsGenerator.generateValidStudent(STUDENT_ID_1);
		Student student2 = ModelsGenerator.generateValidStudent(STUDENT_ID_2);
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(student1);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(student2);
		StudentSimilarity similarity = studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
		assertNotNull(similarity);
		assertEquals(100, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends75Similarity() {
		Student student1 = ModelsGenerator.generateValidStudent(STUDENT_ID_1);
		Student student2 = ModelsGenerator.generate75SimilarityStudent();
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(student1);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(student2);
		StudentSimilarity similarity = studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
		assertNotNull(similarity);
		assertEquals(75, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends50Similarity() {
		Student student1 = ModelsGenerator.generateValidStudent(STUDENT_ID_1);
		Student student2 = ModelsGenerator.generate50SimilarityStudent();
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(student1);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(student2);
		StudentSimilarity similarity = studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
		assertNotNull(similarity);
		assertEquals(50, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends25Similarity() {
		Student student1 = ModelsGenerator.generateValidStudent(STUDENT_ID_1);
		Student student2 = ModelsGenerator.generate25SimilarityStudent();
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(student1);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(student2);
		StudentSimilarity similarity = studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
		assertNotNull(similarity);
		assertEquals(25, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends0Similarity() {
		Student student1 = ModelsGenerator.generateValidStudent(STUDENT_ID_1);
		Student student2 = ModelsGenerator.generate0SimilarityStudent();
		when(studentRepository.findByStudentId(STUDENT_ID_1)).thenReturn(student1);
		when(studentRepository.findByStudentId(STUDENT_ID_2)).thenReturn(student2);
		StudentSimilarity similarity = studentService.compareStudents(STUDENT_ID_1, STUDENT_ID_2);
		assertNotNull(similarity);
		assertEquals(0, similarity.getSimilarityPercentage());
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
