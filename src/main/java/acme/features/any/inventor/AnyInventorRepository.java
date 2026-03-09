
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

public interface AnyInventorRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.id = :id")
	Inventor findInventorById(int id);

	@Query("select count(inv) > 0 from Invention inv where inv.inventor.id = :id and inv.draftMode = false")
	boolean hasPublishedInventions(int id);

}
