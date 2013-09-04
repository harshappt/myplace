package com.harsha.myplace.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harsha.myplace.people.Person;
import com.harsha.myplace.people.PersonRepository;
import com.harsha.myplace.people.PersonResourceAssembler;

@Controller
public class PersonController {

	@Autowired
	private PersonRepository personRepo;

	private PersonResourceAssembler assembler = new PersonResourceAssembler();

	@RequestMapping(value = "/people", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	HttpEntity<Resource<Person>> save(@Validated @RequestBody Person person) {
		Resource<Person> resource = assembler.toResource(personRepo
				.save(person));
		return new ResponseEntity<Resource<Person>>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	HttpEntity<Resources<Resource<Person>>> findAllPeople() {
		Collection<Resource<Person>> personResourceCollection = new ArrayList<Resource<Person>>();
		for (Person person : personRepo.findAll()) {
			personResourceCollection.add(assembler.toResource(person));
		}
		Resources<Resource<Person>> resources = new Resources<Resource<Person>>(
				personResourceCollection);
		return new ResponseEntity<Resources<Resource<Person>>>(resources,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/people/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	HttpEntity<Resource<Person>> findById(@PathVariable String id) {
		Resource<Person> resource = assembler.toResource(personRepo
				.save(personRepo.findOne(id)));
		return new ResponseEntity<Resource<Person>>(resource, HttpStatus.OK);
	}

}
