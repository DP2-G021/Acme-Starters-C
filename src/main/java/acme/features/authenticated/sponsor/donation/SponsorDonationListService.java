
package acme.features.authenticated.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsor;
import acme.entities.sponsorships.Sponsorship;

@Service
public class SponsorDonationListService extends AbstractService<Authenticated, Donation> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected SponsorDonationRepository	repository;

	private Collection<Donation>		donations;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		int userAccountId;
		Sponsorship sponsorship;

		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);
		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		sponsorship = this.repository.findSponsorshipByIdAndSponsorUserAccountId(sponsorshipId, userAccountId);

		status = status && sponsorship != null;
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int sponsorshipId;
		int userAccountId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.donations = this.repository.findDonationsBySponsorshipIdAndSponsorUserAccountId(sponsorshipId, userAccountId);
	}

	@Override
	public void unbind() {
		if (this.donations != null)
			for (final Donation donation : this.donations)
				super.unbindObject(donation, "name", "notes", "money", "kind");
	}

}
