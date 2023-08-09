package de.schauderhaft.jpacomplexity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import static de.schauderhaft.jpacomplexity.DatabaseAssertions.*;
import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaComplexityApplicationTests {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	NamedParameterJdbcOperations jdbc;

	@Autowired
	EntityManager em;

	@Test
	void saveMinion() {

		Minion jens = new Minion();
		jens.name = "Jens";

		em.persist(jens);

		with(jdbc).assertCountOf("minion").isEqualTo(1);

		log.info("Data is safe.");
	}

	@Test
	void savePerson() {

		Person jens = new Person();
		jens.name = "Jens";

		em.persist(jens);

		with(jdbc).assertCountOf("person").isEqualTo(1);

		log.info("Data is safe.");
	}
}
