package de.schauderhaft.jpacomplexity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.Map;

import static de.schauderhaft.jpacomplexity.DatabaseAssertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JpaComplexityTests {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	NamedParameterJdbcOperations jdbc;

	@Autowired
	EntityManager em;

	@Autowired
	TransactionTemplate tx;

	@BeforeEach
	void before() {
		jdbc.update("delete from clothing", Collections.emptyMap());
		jdbc.update("delete from smurf", Collections.emptyMap());
		jdbc.update("delete from minion", Collections.emptyMap());
		jdbc.update("delete from person", Collections.emptyMap());
	}

	@Nested
	@Transactional
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
	@Transactional
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
	}

	@Nested
	@Transactional
	class Question3 {
		@Test
		void queryByNotUpdatedName() {

			Long id = setup();

			em.createQuery("update Minion set name = 'Mark' where id = :id")
					.setParameter("id", id)
					.executeUpdate();

			assertThatThrownBy(
					() -> em.createQuery("select m from Minion m where name = 'Jens'", Minion.class)
							.getSingleResult()
			).isInstanceOf(NoResultException.class);
		}

		@Test
		void queryByUpdatedName() {

			Long id = setup();

			em.createQuery("update Minion set name = 'Mark' where id = :id")
					.setParameter("id", id)
					.executeUpdate();

			Minion markOrJens = em.createQuery("select m from Minion m where name = 'Mark'", Minion.class)
					.getSingleResult();

			assertThat(markOrJens.name)
					.describedAs("Entity doesn't get reloaded")
					.isEqualTo("Jens");
		}
	}

	@Nested
	class Question4 {

		@Test
		void droppingAllCloths() {

			Smurf jokey = tx.execute(tx -> {

				Smurf smurf = new Smurf();
				smurf.name = "Jokey";
				smurf.clothing.add(new Clothing("shoes"));
				smurf.clothing.add(new Clothing("pants"));
				smurf.clothing.add(new Clothing("jacket"));

				em.persist(smurf);
				return smurf;
			});

			Long originalVersion = jokey.version;

			Smurf nakedJokey = tx.execute(tx -> {
				Smurf smurf = em.find(Smurf.class, jokey.id);
				smurf.clothing.clear();
				return smurf;
			});

			assertThat(nakedJokey.version)
					.describedAs("Yes the optimistic locking version gets updated")
					.isNotEqualTo(originalVersion);
		}

		@Test
		void transparentCloths() {

			Smurf jokey = tx.execute(tx -> {

				Smurf smurf = new Smurf();
				smurf.name = "Bob";
				smurf.clothing.add(new Clothing("shoes"));
				smurf.clothing.add(new Clothing("pants"));
				smurf.clothing.add(new Clothing("jacket"));

				em.persist(smurf);
				return smurf;
			});

			Long originalVersion = jokey.version;

			Smurf nakedJokey = tx.execute(tx -> {
				Smurf smurf = em.find(Smurf.class, jokey.id);
				for (Clothing clothing : smurf.clothing) {
					clothing.name = "transparent " + clothing.name;
				}
				return smurf;
			});

			assertThat(nakedJokey.version).isEqualTo(originalVersion);

			Smurf reloaded = tx.execute(tx -> em.find(Smurf.class, jokey.id));
			assertThat(reloaded.version).isEqualTo(originalVersion);
		}
	}

	private Long setup() {

		Minion jens = new Minion();
		jens.name = "Jens";

		em.persist(jens);
		em.flush();
		return jens.id;
	}
}