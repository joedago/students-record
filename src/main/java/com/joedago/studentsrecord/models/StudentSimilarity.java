package com.joedago.studentsrecord.models;

import com.joedago.studentsrecord.persistence.entities.Student;

public class StudentSimilarity {
	
	private Integer similarityPercentage;	
	private Student student1;
	private Student student2;	
	
	public Student getStudent1() {
		return student1;
	}
	public void setStudent1(Student student1) {
		this.student1 = student1;
	}
	public Student getStudent2() {
		return student2;
	}
	public void setStudent2(Student student2) {
		this.student2 = student2;
	}
	public Integer getSimilarityPercentage() {
		return similarityPercentage;
	}
	public void setSimilarityPercentage(Integer similarityPercentage) {
		this.similarityPercentage = similarityPercentage;
	}
	
}
