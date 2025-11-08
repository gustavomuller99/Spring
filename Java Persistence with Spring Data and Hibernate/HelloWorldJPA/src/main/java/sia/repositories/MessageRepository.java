package sia.repositories;

import org.springframework.data.repository.CrudRepository;
import sia.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
