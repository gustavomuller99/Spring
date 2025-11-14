package sia.springdatajpa;

import org.junit.jupiter.api.Test;
import sia.springdatajpa.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindUsersUsingQueriesTest extends SpringDataJpaApplicationTests {

    @Test
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertEquals(12, users.size());
    }

    @Test
    void testFindUser() {
        User mary = userRepository.findByUsername("mary");
        assertEquals("mary", mary.getUsername());
    }

    @Test
    void testFindAllByOrderByUsernameAsc() {
        List<User> users = userRepository.findAllByOrderByUsernameAsc();
        assertAll(
                () -> assertEquals(12, users.size()),
                () -> assertEquals("alex", users.get(0).getUsername()),
                () -> assertEquals("sophia", users.get(users.size() - 1).getUsername())
        );
    }

    @Test
    void testFindByRegistrationDateBetween() {
        List<User> users = userRepository.findByRegistrationDateBetween(
                LocalDate.of(2020, Month.JULY, 1),
                LocalDate.of(2020, Month.DECEMBER, 31));
        assertEquals(2, users.size());
    }
}
