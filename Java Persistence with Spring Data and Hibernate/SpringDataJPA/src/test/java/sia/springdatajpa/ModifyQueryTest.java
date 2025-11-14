package sia.springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import sia.springdatajpa.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModifyQueryTest extends SpringDataJpaApplicationTests {

    @Test
    void testModifyLevel() {
        int updated = userRepository.updateLevel(1, 10);
        List<User> users = userRepository.findByLevel(10, Sort.by("username"));

        assertAll(
                () -> assertEquals(3, updated),
                () -> assertEquals(3, users.size())
        );
    }

    @Test
    void testDeleteByLevel() {
        int deleted = userRepository.deleteByLevel(1);
        List<User> users = userRepository.findByLevel(1, Sort.by("username"));

        assertEquals(0, users.size());
    }

    @Test
    void testDeleteBulkByLevel() {
        int deleted = userRepository.deleteByLevel(1);
        List<User> users = userRepository.findByLevel(1, Sort.by("username"));

        assertEquals(0, users.size());
    }
}
