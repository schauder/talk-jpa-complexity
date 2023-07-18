package de.schauderhaft.jpacomplexity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
class Person {

	@Id
	Long id;

	String name;

	@Version
	Long number;
}
