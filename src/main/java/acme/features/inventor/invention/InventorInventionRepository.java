
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.userAccount.id = :userAccountId")
	Inventor findOneInventorByUserAccountId(int userAccountId);

	@Query("select i from Invention i where i.inventor.id = :inventorId")
	Collection<Invention> findInventionsByInventorId(int inventorId);

	@Query("select i from Invention i where i.id = :id and i.inventor.id = :inventorId")
	Invention findOneInventionByIdAndInventorId(int id, int inventorId);

}
