package sia.springdatajpa;

import org.junit.jupiter.api.Test;
import sia.springdatajpa.model.User;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryResultsTest extends SpringDataJpaApplicationTests {

    @Test
    void testStreamable() {
        try (Stream<User> result = userRepository.findByEmailContaining("ethan")
                .and(userRepository.findByLevel(2))
                .stream().distinct()) {
            assertEquals(4, result.count());
        }
    }
}
