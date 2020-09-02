package com.joedago.studentsrecord.services.impl;

import static com.joedago.studentsrecord.utils.PositionConstants.ACCESS_KEY;
import static com.joedago.studentsrecord.utils.PositionConstants.QUERY;

import java.net.URI;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.joedago.studentsrecord.PositionStackProperties;
import com.joedago.studentsrecord.exceptions.ExceptionMessages;
import com.joedago.studentsrecord.models.PositionResponse;
import com.joedago.studentsrecord.persistence.entities.Student;
import com.joedago.studentsrecord.services.PositionService;

@Service
public class PositionServiceImpl implements PositionService {
	
	private static final Logger LOG = Logger.getLogger(PositionServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	PositionStackProperties properties;
	
	@Override
	public PositionResponse getPositionFromStudent(Student student) {
		URI uri = getRequestUrl(student);
		try {
			return restTemplate.getForObject(uri, PositionResponse.class);
		} catch(HttpStatusCodeException ex) {
			LOG.error(ex.getResponseBodyAsString(), ex);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.POSITION_ERROR);
		} catch(HttpMessageNotReadableException ex) {
			LOG.error(ex.getHttpInputMessage().toString(), ex);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.POSITION_ERROR);
		}
	}
	
	private URI getRequestUrl(Student student) {
		String address = getQueryAddress(student);
		return UriComponentsBuilder.fromHttpUrl(properties.getUrl())
				.path(properties.getForward())
				.queryParam(ACCESS_KEY, properties.getToken())
				.queryParam(QUERY, address).build().toUri();
	}
	
	private String getQueryAddress(Student student) {
		StringBuilder builder = new StringBuilder();
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
		if(student.getCountry() != null) {
			builder.append(student.getCountry());
		}
		return builder.toString();
	}

}
