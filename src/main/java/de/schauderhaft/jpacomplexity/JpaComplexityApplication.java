package de.schauderhaft.jpacomplexity.jpacomplexity;

import de.schauderhaft.jpacomplexity.Demo01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "de.schauderhaft.jpacomplexity")
@EntityScan("de.schauderhaft.jpacomplexity")
public class JpaComplexityApplication implements CommandLineRunner {

	@Autowired
	Demo01 demo;

	public static void main(String[] args) {
		SpringApplication.run(JpaComplexityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		demo.run();


	}
}
