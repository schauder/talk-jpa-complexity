package de.schauderhaft.jpacomplexity;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Demo01 implements Demo{

	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public void run() {

		Person jens = new Person();
		jens.name = "Jens";
		jens.id = 23L;

		em.persist(jens);
	}
}
