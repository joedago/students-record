package com.joedago.studentsrecord.util;

import java.util.Calendar;

import org.apache.commons.lang3.time.DateUtils;

import com.joedago.studentsrecord.model.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entity.Student;

public class StudentUtils {
	
	private StudentUtils() {}

	public static void setAgeAtRegistration(Student student) {
		final Calendar now = Calendar.getInstance();
		final Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(student.getBirthDate());
		final Integer age = now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		student.setAgeAtRegistration(age);
	}
	
	public static StudentSimilarity getStudentSimilarity(Student student1, Student student2) {
		Integer percentage = 0;
		if(student1.getFirstName().equals(student2.getFirstName())) {
			percentage += 25;
		}
		if(student1.getLastName().equals(student2.getLastName())) {
			percentage += 25;
		}
		if(DateUtils.isSameDay(student1.getBirthDate(), student2.getBirthDate())) {
			percentage += 25;
		}
		if(student1.getState().equals(student2.getState())) {
			percentage += 25;
		}
		final StudentSimilarity similarity = new StudentSimilarity();
		similarity.setStudent1(student1);
		similarity.setStudent2(student2);
		similarity.setSimilarityPercentage(percentage);
		return similarity;
	}
	
}
