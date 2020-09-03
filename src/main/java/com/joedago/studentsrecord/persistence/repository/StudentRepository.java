package com.joedago.studentsrecord.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joedago.studentsrecord.persistence.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>  {

	public Student findByEmail(String email);
	public Student findByStudentId(Integer studentId);

}
