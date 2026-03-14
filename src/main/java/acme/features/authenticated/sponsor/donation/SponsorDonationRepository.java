
package acme.features.authenticated.sponsor.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorDonationRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :sponsorshipId and s.sponsor.userAccount.id = :userAccountId")
	Sponsorship findSponsorshipByIdAndSponsorUserAccountId(int sponsorshipId, int userAccountId);

	@Query("select d from Donation d where d.sponsorship.id = :sponsorshipId and d.sponsorship.sponsor.userAccount.id = :userAccountId")
	Collection<Donation> findDonationsBySponsorshipIdAndSponsorUserAccountId(int sponsorshipId, int userAccountId);

	@Query("select d from Donation d where d.id = :id and d.sponsorship.sponsor.userAccount.id = :userAccountId")
	Donation findDonationByIdAndSponsorUserAccountId(int id, int userAccountId);

	@Query("select d from Donation d where d.id = :id")
	Donation findOneDonation(int id);
}
