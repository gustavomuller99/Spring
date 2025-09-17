package tacos.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.web.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
