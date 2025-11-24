package sia.generator.repositories;

import org.springframework.data.repository.CrudRepository;
import sia.subselect.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
