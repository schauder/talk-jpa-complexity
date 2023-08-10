package de.schauderhaft.jpacomplexity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

import java.util.ArrayList;
import java.util.List;

@Entity
class Smurf {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	Long id;

	String name;

	@OneToMany(cascade = CascadeType.ALL)
			@JoinColumn(name = "smurf_id")
	List<Clothing> clothing = new ArrayList<>();

	@Version
	Long version;
}
