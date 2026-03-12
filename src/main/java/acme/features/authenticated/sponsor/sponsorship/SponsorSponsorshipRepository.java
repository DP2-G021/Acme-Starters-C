
package acme.features.authenticated.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.sponsor.userAccount.id = :userAccountId")
	Collection<Sponsorship> findSponsorshipsBySponsorUserAccountId(int userAccountId);

	@Query("select s from Sponsorship s where s.id = :id and s.sponsor.userAccount.id = :userAccountId")
	Sponsorship findSponsorshipByIdAndSponsorUserAccountId(int id, int userAccountId);
}
