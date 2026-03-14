
package acme.features.authenticated.spokesperson.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Repository
public interface SpokespersonMilestoneRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :campaignId and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findCampaignByIdAndSpokespersonUserAccountId(int campaignId, int userAccountId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId and m.campaign.spokesperson.userAccount.id = :userAccountId")
	Collection<Milestone> findMilestonesByCampaignIdAndSpokespersonUserAccountId(int campaignId, int userAccountId);

	@Query("select m from Milestone m where m.id = :id and m.campaign.spokesperson.userAccount.id = :userAccountId")
	Milestone findMilestoneByIdAndSpokespersonUserAccountId(int id, int userAccountId);

}
