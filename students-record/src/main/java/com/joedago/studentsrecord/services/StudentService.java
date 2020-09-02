package com.joedago.studentsrecord.services;

import com.joedago.studentsrecord.models.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entities.Student;

public interface StudentService {
	
	public void saveStudent(Student student);
	public StudentSimilarity compareStudents(Integer studentId1, Integer studentId2);

}
