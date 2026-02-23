
package acme.entities.campaigns;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import acme.client.repositories.AbstractRepository;

public interface MilestoneRepository extends AbstractRepository {

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double getCampaignEffort(@Param("campaignId") int campaignId);

}
