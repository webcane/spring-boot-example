package cane.brothers.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cane.brothers.spring.model.Item;

/**
 * Allow to do rest http requests without items controller
 * 
 * @author Mikhail Niedre
 */
@RepositoryRestResource(collectionResourceRel = "items", path = "items")
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByName(@Param("name") String name);
}
