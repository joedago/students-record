package com.joedago.studentsrecord.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "student", schema = "students_record")
public class Student {
	
	@Id
	@Column(name = "studentid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer studentId;
	@Email
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;
	@NotBlank
	@Column(name = "firstname", length = 45, nullable = false, unique = false)
	private String firstName;
	@NotBlank
	@Column(name = "lastname", length = 45, nullable = false, unique = false)
	private String lastName;
	@NotNull
	@Column(name = "birthdate", length = 45, nullable = false, unique = false)
	private Date birthDate;
	@NotBlank
	@Column(name = "streetaddress1", length = 200, nullable = false, unique = false)
	private String streetAddress1;
	@Column(name = "streetaddress2", length = 100, nullable = true, unique = false)
	private String streetAddress2;
	@NotBlank
	@Column(name = "city", length = 100, nullable = false, unique = false)
	private String city;
	@NotBlank
	@Column(name = "state", length = 45, nullable = false, unique = false)
	private String state;
	@NotBlank
	@Column(name = "country", length = 45, nullable = false, unique = false)
	private String country;
	@NotNull
	@Column(name = "zipcode", nullable = false, unique = false)
	private Integer zipCode;
	@Column(name = "latitude")
	private Double latitude;
	@Column(name = "longitude")
	private Double longitude;
	@Column(name = "ageatregistration")
	private Integer ageAtRegistration;
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getStreetAddress1() {
		return streetAddress1;
	}
	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}
	public String getStreetAddress2() {
		return streetAddress2;
	}
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getAgeAtRegistration() {
		return ageAtRegistration;
	}
	public void setAgeAtRegistration(Integer ageAtRegistration) {
		this.ageAtRegistration = ageAtRegistration;
	}
	
}
