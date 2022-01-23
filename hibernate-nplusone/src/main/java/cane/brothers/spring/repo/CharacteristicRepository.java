package cane.brothers.spring.repo;

import cane.brothers.spring.model.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Mikhail Niedre
 */
@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

	/**
	 * Hibernate: select characteri0_.id as id1_0_, characteri0_.item_id as item_id3_0_, characteri0_.type as type2_0_
	 * from characteristics characteri0_ cross join items item1_ where characteri0_.item_id=item1_.id and item1_.name=?
	 *
	 * Hibernate: select item0_.id as id1_1_0_, item0_.description as descript2_1_0_, item0_.name as name3_1_0_
	 * from items item0_ where item0_.id=?
	 *
	 * @param itemName
	 * @return
	 */
	@Query("SELECT c FROM CHARACTERISTICS c WHERE c.item.name = :itemName")
	List<Characteristic> findCharacteristicsByItemName(@Param("itemName") String itemName);
}
