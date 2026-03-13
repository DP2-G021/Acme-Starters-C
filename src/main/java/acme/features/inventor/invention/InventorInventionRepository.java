
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.parts.Part;
import acme.realms.Inventor;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.userAccount.id = :userAccountId")
	Inventor findOneInventorByUserAccountId(int userAccountId);

	@Query("select i from Invention i where i.id = :id")
	Invention findOneInventionById(int id);

	@Query("select i from Invention i where i.inventor.id = :inventorId")
	Collection<Invention> findManyInventionsByInventorId(int inventorId);

	@Query("select i from Invention i where i.id = :id and i.inventor.id = :inventorId")
	Invention findOneInventionByIdAndInventorId(int id, int inventorId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findManyPartsByInventionId(int inventionId);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	Long countPartsByInventionId(int inventionId);
}
