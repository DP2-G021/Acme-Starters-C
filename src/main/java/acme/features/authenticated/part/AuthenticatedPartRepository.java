
package acme.features.authenticated.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.parts.Part;
import acme.realms.Inventor;

@Repository
public interface AuthenticatedPartRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.userAccount.id = :userAccountId")
	Inventor findOneInventorByUserAccountId(int userAccountId);

	@Query("select i from Invention i where i.id = :id and i.inventor.id = :inventorId")
	Invention findOneInventionByIdAndInventorId(int id, int inventorId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("select p from Part p where p.id = :id and p.invention.inventor.id = :inventorId")
	Part findOnePartByIdAndInventorId(int id, int inventorId);

}
