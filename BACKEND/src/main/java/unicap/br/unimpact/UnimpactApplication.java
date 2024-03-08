package unicap.br.unimpact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UnimpactApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnimpactApplication.class, args);
	}

}
