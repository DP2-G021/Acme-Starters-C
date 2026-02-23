
package acme.entities.inventions;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface InventionRepository extends AbstractRepository {

	//Looking for the ticker of the Invention
	@Query("select i from Invention i where i.ticker= :ticker ")
	Invention findInventionByTicker(String ticker);

	//Query in order to know how many part does a invention have
	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	Long countPartsByInventionId(int inventionId);

}
