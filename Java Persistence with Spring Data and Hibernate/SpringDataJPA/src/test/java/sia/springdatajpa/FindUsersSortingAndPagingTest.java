package sia.springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import sia.springdatajpa.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindUsersSortingAndPagingTest extends SpringDataJpaApplicationTests {

    @Test
    void testOrder() {
        User user1 = userRepository.findFirstByOrderByUsernameAsc();
        User user2 = userRepository.findTopByOrderByRegistrationDateDesc();
        Page<User> users = userRepository.findAll(PageRequest.of(1, 3));
        List<User> userList = userRepository.findFirst2ByLevel(2, Sort.by("registrationDate"));

        assertAll(
                () -> assertEquals("alex", user1.getUsername()),
                () -> assertEquals("emma", user2.getUsername()),
                () -> assertEquals(2, userList.size()),
                () -> assertEquals(3, users.getSize()),
                () -> assertEquals("lucas", userList.get(0).getUsername()),
                () -> assertEquals("ethan", userList.get(1).getUsername())
        );
    }

    @Test
    void testFindByLevel() {
        Sort.TypedSort<User> user = Sort.sort(User.class);

        // type safe
        List<User> users = userRepository.findByLevel(3, user.by(User::getRegistrationDate).descending());
        assertAll(
                () -> assertEquals(2, users.size()),
                () -> assertEquals("alex", users.get(0).getUsername())
        );
    }

    @Test
    void testFindByActive() {
        List<User> users = userRepository.findByActive(true, PageRequest.of(0, 4,  Sort.by("registrationDate")));
        assertAll(
                () -> assertEquals(4, users.size()),
                () -> assertEquals("isabella", users.get(0).getUsername())
        );
    }
}
