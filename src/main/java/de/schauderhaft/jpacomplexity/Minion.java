package de.schauderhaft.jpacomplexity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

import java.util.ArrayList;
import java.util.List;

@Entity
class Minion {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	Long id;

	String name;


	@Version
	Long version;
}
