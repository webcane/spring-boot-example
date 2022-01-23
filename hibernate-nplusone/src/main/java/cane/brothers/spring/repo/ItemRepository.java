package cane.brothers.spring.repo;

import cane.brothers.spring.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Allow to do rest http requests without items controller
 * 
 * @author Mikhail Niedre
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByName(@Param("name") String name);

	@Query("SELECT i from ITEMS i")
	List<Item> findAllItems();

	Item findByCharacteristics(long characteristicId);
}
