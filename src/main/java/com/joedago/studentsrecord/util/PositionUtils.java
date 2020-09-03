package com.joedago.studentsrecord.util;

import static com.joedago.studentsrecord.util.PositionConstants.ACCESS_KEY;
import static com.joedago.studentsrecord.util.PositionConstants.QUERY;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

import com.joedago.studentsrecord.persistence.entity.Student;
import com.joedago.studentsrecord.property.PositionStackProperties;

public class PositionUtils {

	private PositionUtils() {}
	
	public static URI getRequestUrl(Student student, PositionStackProperties properties) {
		final String address = getQueryAddress(student);
		return UriComponentsBuilder.fromHttpUrl(properties.getUrl())
				.path(properties.getForward())
				.queryParam(ACCESS_KEY, properties.getToken())
				.queryParam(QUERY, address).build().toUri();
	}
	
	private static String getQueryAddress(Student student) {
		final StringBuilder builder = new StringBuilder();
		if(student.getStreetAddress1() != null) {
			builder.append(student.getStreetAddress1());
		}
		if(student.getStreetAddress2() != null) {
			builder.append(" ");
			builder.append(student.getStreetAddress2());
		}
		builder.append(", ");
		if(student.getCity() != null) {
			builder.append(student.getCity());
			builder.append(" ");
		}
		if(student.getState() != null) {
			builder.append(student.getState());
			builder.append(" ");
		}
		if(student.getZipCode() != null) {
			builder.append(student.getZipCode());
			builder.append(" ");
		}
		
		return builder.toString();
	}
}
