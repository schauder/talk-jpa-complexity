package de.schauderhaft.jpacomplexity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "de.schauderhaft.jpacomplexity")
@EntityScan("de.schauderhaft.jpacomplexity")
public class JpaComplexityApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JpaComplexityApplication.class, args);
	}

	@Override
	public void run(String... args) {

	}
}
