
package acme.features.authenticated.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Authenticated, Sponsorship> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	private Sponsor							sponsor;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		status = this.sponsorship != null && this.sponsorship.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.sponsor = this.repository.findSponsorByUserAccountId(userAccountId);
		if (this.sponsor == null)
			this.sponsorship = null;
		else
			this.sponsorship = this.repository.findOneSponsorshipByIdAndSponsorshipId(id, this.sponsor.getId());
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		Collection<Donation> donations;
		donations = this.repository.findDonationsBySponsorshipId(this.sponsorship.getId());

		this.repository.deleteAll(donations);
		this.repository.delete(this.sponsorship);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney", "draftMode");
		tuple.put("sponsorshipId", this.sponsorship.getId());
	}

}
