package com.harsha.myplace.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.harsha.myplace.controllers.PersonController;
import com.harsha.myplace.people.Address;
import com.harsha.myplace.people.Person;
import com.harsha.myplace.people.PersonRepository;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

	private MockMvc mockmvc;

	@Mock
	private PersonRepository repo;

	@Before
	public void setup() {
		mockmvc = standaloneSetup(new PersonController()).build();
	}

	@Test
	public void testSave() throws Exception {
		Person personToSave = createPerson();

		when(repo.save(personToSave)).thenReturn(personToSave);

		// mockmvc.perform(
		// post("/person/save").contentType(MediaType.APPLICATION_JSON)
		// .content(personToSave))
		// .andExpect(jsonPath("$.lastName").exists()).andDo(print());

	}

	private Person createPerson() throws JsonGenerationException,
			JsonMappingException, IOException {

		Person person = new Person();
		person.setFirstName("435436456");
		person.setEmail("xyz@yahoo.com");
		person.setLastName("Puthalapattu");
		person.setPassword("iiiii");
		person.setPrimaryPhone("1234556789");
		person.setPresentAddress(new Address());

		return person;
	}


}
