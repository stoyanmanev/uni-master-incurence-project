package uni.fmi.masters.project.insurenceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class InsurenceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsurenceProjectApplication.class, args);
	}

}
