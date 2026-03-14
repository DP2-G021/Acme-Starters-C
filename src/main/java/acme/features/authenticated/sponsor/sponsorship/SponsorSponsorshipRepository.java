
package acme.features.authenticated.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.sponsor.userAccount.id = :userAccountId")
	Collection<Sponsorship> findSponsorshipsBySponsorUserAccountId(int userAccountId);

	@Query("select s from Sponsorship s where s.id = :id and s.sponsor.userAccount.id = :userAccountId")
	Sponsorship findSponsorshipByIdAndSponsorUserAccountId(int id, int userAccountId);

	@Query("select s from Sponsor s where s.userAccount.id = :userAccountId")
	Sponsor findSponsorByUserAccountId(int userAccountId);

	@Query("select d from Donation d where d.sponsorship.id = :sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);
}
