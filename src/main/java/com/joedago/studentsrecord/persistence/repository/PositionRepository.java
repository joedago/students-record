package com.joedago.studentsrecord.persistence.repository;

import com.joedago.studentsrecord.model.PositionResponse;
import com.joedago.studentsrecord.persistence.entity.Student;

public interface PositionRepository {

	public PositionResponse getPositionFromStudent(Student student); 
}
