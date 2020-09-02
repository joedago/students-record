package com.joedago.studentsrecord.services;

import com.joedago.studentsrecord.models.PositionResponse;
import com.joedago.studentsrecord.persistence.entities.Student;

public interface PositionService {

	public PositionResponse getPositionFromStudent(Student student); 
}
