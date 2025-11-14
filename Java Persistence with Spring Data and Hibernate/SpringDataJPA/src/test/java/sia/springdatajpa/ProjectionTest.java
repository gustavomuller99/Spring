package sia.springdatajpa;

import org.junit.jupiter.api.Test;
import sia.springdatajpa.model.Projection;
import sia.springdatajpa.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProjectionTest extends SpringDataJpaApplicationTests {

    @Test
    void testProjectionUsername() {

        List<Projection.UsernameOnly> users = userRepository.findByEmail("isabella@corp.net");

        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("isabella", users.get(0).getUsername())
        );
    }

    @Test
    void testProjectionUserSummary() {

        List<Projection.UserSummary> users = userRepository.findByRegistrationDateAfter(LocalDate.of(2023, Month.JULY, 30));

        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("emma", users.get(0).getUsername()),
                () -> assertEquals("emma emma@sample.io", users.get(0).getInfo())
        );
    }

    @Test
    void testDynamicProjection() {
        List<Projection.UsernameOnly> usernames = userRepository.findByEmail("emma@sample.io", Projection.UsernameOnly.class);
        List<User> users = userRepository.findByEmail("isabella@corp.net", User.class);

        assertAll(
                () -> assertEquals(1, usernames.size()),
                () -> assertEquals("emma", usernames.get(0).getUsername()),
                () -> assertEquals(1, users.size()),
                () -> assertEquals("isabella", users.get(0).getUsername())
        );
    }
}
