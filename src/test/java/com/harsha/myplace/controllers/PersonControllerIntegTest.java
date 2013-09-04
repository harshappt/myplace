package com.harsha.myplace.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONValue;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.harsha.myplace.config.DispatcherServletSubstitute;
import com.harsha.myplace.config.RootApplicationContext;
import com.harsha.myplace.people.Address;
import com.harsha.myplace.people.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextHierarchy({
		@ContextConfiguration(classes = { RootApplicationContext.class }),
		@ContextConfiguration(classes = { DispatcherServletSubstitute.class }) })
@ActiveProfiles("test")
public class PersonControllerIntegTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockmvc;

	@Before
	public void setup() {
		mockmvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void testValidations() throws Exception {
		Person person = createPerson();
		person.setFirstName("");
		person.setLastName("");
		person.setEmail("");
		person.setPassword("");

		String string = JSONify(person);
		mockmvc.perform(
				post("/people").contentType(MediaType.APPLICATION_JSON)
						.content(string))
				.andExpect(jsonPath("$.lastName").exists())
				.andExpect(jsonPath("$.firstName").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.email").exists()).andDo(print());

	}

	@Test
	public void testSave() throws Exception {
		Person person = createPerson();
		String string = JSONify(person);
		ResultActions perform = mockmvc.perform(post("/people").contentType(
				MediaType.APPLICATION_JSON).content(string));

		perform.andExpect(jsonPath("$.id").exists()).andDo(print());
	}

	@Test
	public void testFidnById() throws Exception {
		Person person = createPerson();
		String string = JSONify(person);
		String contentAsString = mockmvc
				.perform(
						post("/people").contentType(MediaType.APPLICATION_JSON)
								.content(string))
				.andExpect(jsonPath("$.id").exists()).andReturn().getResponse()
				.getContentAsString();
		Map parse = (HashMap) JSONValue.parse(contentAsString);
		mockmvc.perform(get("/people/{id}", parse.get("id")))
				.andExpect(jsonPath("$.links").exists()).andDo(print());
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

	private String JSONify(Object obj) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		return objectWriter.writeValueAsString(obj);
	}

	private HashMap objectify(String string) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(string, HashMap.class);
	}
}
