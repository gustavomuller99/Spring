package sia.springdatajpa;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sia.springdatajpa.model.User;
import sia.springdatajpa.repositories.UserRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class SpringDataJpaApplicationTests {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void beforeAll() {
        userRepository.saveAll(generateUsers());
    }

    private static List<User> generateUsers() {
        List<User> users = new ArrayList<>();

        User john = new User("john", LocalDate.of(2020, Month.APRIL, 13));
        john.setEmail("john@somedomain.com");
        john.setLevel(1);
        john.setActive(true);

        User mary = new User("mary", LocalDate.of(2021, Month.JANUARY, 5));
        mary.setEmail("mary@example.com");
        mary.setLevel(2);
        mary.setActive(true);

        User alex = new User("alex", LocalDate.of(2019, Month.SEPTEMBER, 22));
        alex.setEmail("alex@domain.net");
        alex.setLevel(3);
        alex.setActive(false);

        User sophia = new User("sophia", LocalDate.of(2022, Month.MARCH, 14));
        sophia.setEmail("sophia@mail.org");
        sophia.setLevel(1);
        sophia.setActive(true);

        User michael = new User("michael", LocalDate.of(2020, Month.NOVEMBER, 30));
        michael.setEmail("michael@company.com");
        michael.setLevel(4);
        michael.setActive(false);

        User lucas = new User("lucas", LocalDate.of(2018, Month.MAY, 9));
        lucas.setEmail("lucas@webmail.com");
        lucas.setLevel(2);
        lucas.setActive(true);

        User emma = new User("emma", LocalDate.of(2023, Month.AUGUST, 1));
        emma.setEmail("emma@sample.io");
        emma.setLevel(5);
        emma.setActive(true);

        User oliver = new User("oliver", LocalDate.of(2021, Month.JUNE, 18));
        oliver.setEmail("oliver@company.org");
        oliver.setLevel(1);
        oliver.setActive(false);

        User isabella = new User("isabella", LocalDate.of(2017, Month.DECEMBER, 3));
        isabella.setEmail("isabella@corp.net");
        isabella.setLevel(3);
        isabella.setActive(true);

        User ethan = new User("ethan", LocalDate.of(2019, Month.FEBRUARY, 25));
        ethan.setEmail("ethan@demo.com");
        ethan.setLevel(2);
        ethan.setActive(false);

        users.add(john);
        users.add(mary);
        users.add(alex);
        users.add(sophia);
        users.add(michael);
        users.add(lucas);
        users.add(emma);
        users.add(oliver);
        users.add(isabella);
        users.add(ethan);

        return users;
    }

    @AfterAll
    void afterAll() {
        userRepository.deleteAll();
    }

}
