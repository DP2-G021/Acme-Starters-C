
package acme.entities.parts;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface PartRepository extends AbstractRepository {

	//SELECTING THE SUM OF THE COSTS
	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :inventionId")
	Double getInventionCost(int inventionId);

}
