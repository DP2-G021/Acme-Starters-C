
package acme.features.authenticated.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.spokesperson.userAccount.id = :userAccountId")
	Collection<Campaign> findCampaignsBySpokespersonUserAccountId(int userAccountId);

	@Query("select c from Campaign c where c.id = :id and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findCampaignByIdAndSpokespersonUserAccountId(int id, int userAccountId);

}
