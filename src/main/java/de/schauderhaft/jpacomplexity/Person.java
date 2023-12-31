package de.schauderhaft.jpacomplexity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
class Person {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;

	String name;

	@Version
	Long number;
}
