package de.schauderhaft.jpacomplexity;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

@Component
public class Demo01_b implements Demo {

	Logger log = LoggerFactory.getLogger(Demo01_b.class);

	@Autowired
	EntityManager em;

	@Autowired
	NamedParameterJdbcOperations jdbc;

	@Override
	@Transactional
	public void run() {

		Person jens = new Person();
		jens.name = "Jens";

		em.persist(jens);

		assertThat(
				jdbc.queryForObject(
						"select count(*) from person",
						emptyMap(),
						Integer.class)
		).isEqualTo(1);

		log.info("Data is safe.");
	}
}
