package com.harsha.myplace.people;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends
		PagingAndSortingRepository<Person, String>,
		CrudRepository<Person, String> {

	Person findByEmail(String email);

}
