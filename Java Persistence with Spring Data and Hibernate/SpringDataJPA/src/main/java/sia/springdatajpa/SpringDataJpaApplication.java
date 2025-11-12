package sia.springdatajpa;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sia.springdatajpa.model.User;
import sia.springdatajpa.repositories.UserRepository;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    public ApplicationRunner configure(UserRepository userRepository) {
        return args -> {
            User user1 = new User("beth", LocalDate.of(2020, Month.AUGUST, 3));
            User user2 = new User("mike", LocalDate.of(2020, Month.JANUARY, 18));
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.findAll().forEach(System.out::println);
        };
    }
}
