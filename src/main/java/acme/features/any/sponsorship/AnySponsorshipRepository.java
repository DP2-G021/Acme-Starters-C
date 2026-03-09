
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.draftMode = false")
	Collection<Sponsorship> findAnyPublished();

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

}
