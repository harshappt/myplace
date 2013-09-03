package com.harsha.myplace.people;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.harsha.myplace.controllers.PersonController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo; 
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PersonResourceAssembler implements ResourceAssembler<Person, Resource<Person>> {

	private Class<PersonController> controllerClass = PersonController.class;
	public static final String PERSON_REL = "people";
	@Override
	public Resource<Person> toResource(Person entity) {
		Resource<Person> resource = new Resource<Person>(entity);
		Link selfLink = linkTo(methodOn(controllerClass).findById(entity.getId())).withSelfRel();
		resource.add(selfLink);
		return resource;
	}

}
