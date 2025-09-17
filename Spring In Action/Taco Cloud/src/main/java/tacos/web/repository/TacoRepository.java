package tacos.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.web.entities.Taco;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
