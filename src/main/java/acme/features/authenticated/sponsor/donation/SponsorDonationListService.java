package acme.features.authenticated.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationListService extends AbstractService<Authenticated, Donation> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected SponsorDonationRepository	repository;

	private Collection<Donation>		donations;
	private Sponsorship             	sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);
		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.sponsorship = this.repository.findSponsorshipByIdAndSponsorUserAccountId(sponsorshipId, userAccountId);

		status = status && sponsorship != null;
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int sponsorshipId;
		int userAccountId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.sponsorship = this.repository.findSponsorshipByIdAndSponsorUserAccountId(sponsorshipId, userAccountId);
		this.donations = this.repository.findDonationsBySponsorshipIdAndSponsorUserAccountId(sponsorshipId, userAccountId);
	}

	@Override
	public void unbind() {
		if (this.donations != null)
			for (final Donation donation : this.donations)
				super.unbindObject(donation, "name", "notes", "money", "kind");

		if (this.sponsorship != null) {
			super.unbindGlobal("sponsorshipId", this.sponsorship.getId());
			super.unbindGlobal("showCreate", this.sponsorship.getDraftMode());
		}
	}

}