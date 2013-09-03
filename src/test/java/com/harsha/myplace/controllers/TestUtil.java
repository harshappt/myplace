package com.harsha.myplace.controllers;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class TestUtil {

	public static String JSONify(Object obj) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		return objectWriter.writeValueAsString(obj);
	}
}
