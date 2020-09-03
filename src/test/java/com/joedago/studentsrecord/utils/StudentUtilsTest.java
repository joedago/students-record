package com.joedago.studentsrecord.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.joedago.studentsrecord.model.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entity.Student;
import com.joedago.studentsrecord.util.StudentUtils;

class StudentUtilsTest {
	
	@Test
	void testSetAgeAtRegistration() {
		final Student student = ModelsGenerator.generateValidStudent();
		StudentUtils.setAgeAtRegistration(student);
		assertEquals(ModelsGenerator.STUDENT_AGE, student.getAgeAtRegistration());
	}
	
	@Test
	void testCompareStutends100Similarity() {
		final Student student1 = ModelsGenerator.generateValidStudent();
		final Student student2 = ModelsGenerator.generateValidStudent();
		final StudentSimilarity similarity = StudentUtils.getStudentSimilarity(student1, student2);
		assertNotNull(similarity);
		assertEquals(100, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends75Similarity() {
		final Student student1 = ModelsGenerator.generateValidStudent();
		final Student student2 = ModelsGenerator.generate75SimilarityStudent();
		final StudentSimilarity similarity = StudentUtils.getStudentSimilarity(student1, student2);
		assertNotNull(similarity);
		assertEquals(75, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends50Similarity() {
		final Student student1 = ModelsGenerator.generateValidStudent();
		final Student student2 = ModelsGenerator.generate50SimilarityStudent();
		final StudentSimilarity similarity = StudentUtils.getStudentSimilarity(student1, student2);
		assertNotNull(similarity);
		assertEquals(50, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends25Similarity() {
		final Student student1 = ModelsGenerator.generateValidStudent();
		final Student student2 = ModelsGenerator.generate25SimilarityStudent();
		final StudentSimilarity similarity = StudentUtils.getStudentSimilarity(student1, student2);
		assertNotNull(similarity);
		assertEquals(25, similarity.getSimilarityPercentage());
	}
	
	@Test
	void testCompareStutends0Similarity() {
		Student student1 = ModelsGenerator.generateValidStudent();
		Student student2 = ModelsGenerator.generate0SimilarityStudent();
		final StudentSimilarity similarity = StudentUtils.getStudentSimilarity(student1, student2);
		assertNotNull(similarity);
		assertEquals(0, similarity.getSimilarityPercentage());
	}

}
