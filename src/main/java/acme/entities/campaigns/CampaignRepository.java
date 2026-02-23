
package acme.entities.campaigns;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(@Param("ticker") String ticker);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Long countMilestonesByCampaignId(@Param("campaignId") int campaignId);

	@Query("select sum(m.expectedPercentage) from Milestone m where m.campaign.id = :campaignId")
	Double calculateTotalEffort(@Param("campaignId") int campaignId);

}
