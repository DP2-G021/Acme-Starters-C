
package acme.features.any.campaigns;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;

@Repository
public interface AnyCampaignRepository extends AbstractRepository {

	// Para el List: busca las campañas que NO están en modo borrador (publicadas)
	@Query("select c from Campaign c where c.draftMode = false")
	Collection<Campaign> findAnyPublished();

	// Para el Show: busca una campaña concreta por su ID
	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);
}
