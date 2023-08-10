package de.schauderhaft.jpacomplexity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

import static de.schauderhaft.jpacomplexity.DatabaseAssertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaComplexityTests {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	NamedParameterJdbcOperations jdbc;

	@Autowired
	EntityManager em;

	@Nested
	class Question1 {
		@Test
		void saveMinion() {

			Minion jens = new Minion();
			jens.name = "Jens";

			em.persist(jens);

			with(jdbc).assertCountOf("minion")
					.describedAs("Nothing saved in the database")
					.isEqualTo(0);

			log.info("Data is safe.");
		}

		@Test
		void savePerson() {

			Person jens = new Person();
			jens.name = "Jens";

			em.persist(jens);

			with(jdbc).assertCountOf("person")
					.describedAs("Of course, it is saved in the database.")
					.isEqualTo(1);

			log.info("Data is safe.");
		}
	}

	@Nested
	class Question2 {
		@Test
		void loadInserted() {


			jdbc.update("insert minion (id, name) values (23, 'Jens')", Collections.emptyMap());

			Minion jens = em.find(Minion.class, 23);

			assertThat(jens.name)
					.describedAs("Entity does get loaded.")
					.isEqualTo("Jens");
		}

		@Test
		void loadUpdated() {

			Long id = setup();

			jdbc.update("update minion set name = 'Mark' where id = :id", Map.of("id", id));

			Minion markOrJens = em.find(Minion.class, id);

			assertThat(markOrJens.name)
					.describedAs("Entity doesn't get reloaded")
					.isEqualTo("Jens");
		}

		private Long setup() {

			Minion jens = new Minion();
			jens.name = "Jens";

			em.persist(jens);
			em.flush();
			return jens.id;
		}
	}

	@Nested
	class Question3 {
		@Test
		void queryUpdated() {

			Long id = setup();

			em.createQuery("update Minion set name = 'Mark' where id = :id")
					.setParameter("id", id)
					.executeUpdate();

			Minion markOrJens = em.createQuery("select m from Minion m where name = 'Mark'", Minion.class).getSingleResult();

			assertThat(markOrJens.name)
					.describedAs("Entity doesn't get reloaded")
					.isEqualTo("Jens");
		}

		private Long setup() {

			Minion jens = new Minion();
			jens.name = "Jens";

			em.persist(jens);
			em.flush();
			return jens.id;
		}
	}
}