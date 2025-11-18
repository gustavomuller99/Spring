package sia.generator.repositories;

import org.springframework.data.repository.CrudRepository;
import sia.generator.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
