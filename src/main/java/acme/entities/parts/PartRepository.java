
package acme.entities.parts;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import acme.client.repositories.AbstractRepository;

public interface PartRepository extends AbstractRepository {

	//SELECTING THE SUM OF THE COSTS
	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :inventionId")
	Double getInventionCost(@Param("inventionId") int inventionId);

	//This is a Set that gives us every currency of the database in order to find all that are different from EUR
	@Query("select distinct p.cost.currency from Part p where p.invention.id = :inventionId")
	Set<String> getCurrencyOfAllCost(@Param("inventionId") int inventionId);

}
