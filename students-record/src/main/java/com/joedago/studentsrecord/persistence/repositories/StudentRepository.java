package com.joedago.studentsrecord.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.joedago.studentsrecord.persistence.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Integer>  {
	

}
