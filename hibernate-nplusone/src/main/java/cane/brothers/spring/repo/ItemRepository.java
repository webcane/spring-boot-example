package cane.brothers.spring.repo;

import cane.brothers.spring.model.Item;
import org.springframework.data.jpa.repository.EntityGraph;
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

	List<Item> findAllBy();

	@Query("SELECT i from ITEMS i LEFT JOIN FETCH i.characteristics")
	List<Item> fetchAllItems();

	@EntityGraph(attributePaths = {"characteristics"})
	List<Item> findAll();

	Item findByCharacteristics(long characteristicId);
}
