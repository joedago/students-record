package com.joedago.studentsrecord.persistence.repository.impl;

import java.net.URI;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.studentsrecord.exception.ExceptionMessages;
import com.joedago.studentsrecord.model.PositionResponse;
import com.joedago.studentsrecord.persistence.entity.Student;
import com.joedago.studentsrecord.persistence.repository.PositionRepository;
import com.joedago.studentsrecord.property.PositionStackProperties;
import com.joedago.studentsrecord.util.PositionUtils;

@Service
public class PositionRepositoryImpl implements PositionRepository {
	
	private static final Logger LOG = Logger.getLogger(PositionRepositoryImpl.class);

	private final RestTemplate restTemplate;
	
	private final PositionStackProperties properties;

	@Autowired
	public PositionRepositoryImpl(RestTemplate restTemplate, PositionStackProperties properties) {
		this.restTemplate = restTemplate;
		this.properties = properties;
	}
	
	@Override
	public PositionResponse getPositionFromStudent(Student student) {
		final URI uri = PositionUtils.getRequestUrl(student, properties);
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
	
}
