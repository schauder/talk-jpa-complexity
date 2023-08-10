package de.schauderhaft.jpacomplexity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Clothing {

	Clothing(){};

	Clothing(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Long id;
	String name;
}
