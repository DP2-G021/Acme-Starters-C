
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Repository
public interface AnyMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :id")
	Milestone findOneMilestoneById(int id);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);
}
