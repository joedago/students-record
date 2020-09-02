package com.joedago.studentsrecord.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joedago.studentsrecord.Application;
import com.joedago.studentsrecord.models.StudentSimilarity;
import com.joedago.studentsrecord.persistence.entities.Student;
import com.joedago.studentsrecord.services.StudentService;
import com.joedago.studentsrecord.utils.ModelsGenerator;

@AutoConfigureMockMvc
@WebMvcTest(controllers = StudentControllerTest.class)
@ContextConfiguration(classes = Application.class)
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private StudentService studentService;
	
	@Test
	void testSaveStudent() throws Exception {
		Mockito.doNothing().when(studentService).saveStudent(Mockito.any(Student.class));
		mockMvc.perform(post(ControllerConstants.STUDENT_URI))
		.andExpect(status().isCreated())
		.andReturn();
	}
	
	@Test
	void testCompareStudents() throws Exception {
		StudentSimilarity similarity = ModelsGenerator.generateStudentSimilarity();
		when(studentService.compareStudents(Mockito.anyInt(), Mockito.anyInt())).thenReturn(similarity);
		MvcResult result = mockMvc.perform(
				get(ControllerConstants.STUDENT_URI + ControllerConstants.STUDENT_COMPARE)
				.param(ControllerConstants.STUDENT_ID_1, "1")
				.param(ControllerConstants.STUDENT_ID_2, "2")
				)
				.andExpect(status().isOk())
				.andReturn();
		EntityModel<StudentSimilarity> response = objectMapper.readValue(result.getResponse().getContentAsString(), 
									objectMapper.getTypeFactory().constructParametricType(EntityModel.class, StudentSimilarity.class));
		assertNotNull(response);
	}
}
