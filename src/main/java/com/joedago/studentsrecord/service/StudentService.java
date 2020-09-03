package com.joedago.studentsrecord.service;

import com.joedago.studentsrecord.model.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entity.Student;

public interface StudentService {
	
	public void saveStudent(Student student);
	public StudentSimilarity compareStudents(Integer studentId1, Integer studentId2);

}
