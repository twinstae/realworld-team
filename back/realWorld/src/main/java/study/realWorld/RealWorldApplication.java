package study.realWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableJpaAuditing
@SpringBootApplication
public class RealWorldApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealWorldApplication.class, args);
	}
}
