
package acme.features.any.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.parts.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("select i from Invention i where i.id = :id and i.draftMode = false")
	Invention findPublishedInventionById(int id);

	@Query("select p from Part p where p.invention.id = :inventionId and p.invention.draftMode = false order by p.name asc")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("select p from Part p where p.id = :id and p.invention.draftMode = false")
	Part findPublishedPartById(int id);

}
