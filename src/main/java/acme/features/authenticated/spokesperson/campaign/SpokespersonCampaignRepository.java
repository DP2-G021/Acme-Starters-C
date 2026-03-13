
package acme.features.authenticated.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.spokesperson.userAccount.id = :userAccountId")
	Collection<Campaign> findCampaignsBySpokespersonUserAccountId(int userAccountId);

	@Query("select c from Campaign c where c.id = :id and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findCampaignByIdAndSpokespersonUserAccountId(int id, int userAccountId);

	// Para la publicación en cascada: obtener todos los hitos de una campaña específica
	@Query("select s from Spokesperson s where s.userAccount.id = :userAccountId")
	Spokesperson findSpokespersonByUserAccountId(int userAccountId);

	// Para el CreateService: obtener el perfil de Spokesperson del usuario actual
	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);
}
