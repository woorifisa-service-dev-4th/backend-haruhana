package site.haruhana.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HaruhanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaruhanaApplication.class, args);
    }
}