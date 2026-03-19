
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;

@Repository
public interface AnyInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.draftMode = false order by i.ticker asc")
	Collection<Invention> findPublishedInventions();

	@Query("select i from Invention i where i.id = :id and i.draftMode = false")
	Invention findPublishedInventionById(int id);
}
