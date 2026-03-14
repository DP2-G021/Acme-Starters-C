
package acme.features.authenticated.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.DonationKind;
import acme.realms.Sponsor;

@Service
public class SponsorDonationUpdateService extends AbstractService<Authenticated, Donation> {

	//Internal state ---------------------------------------------------------
	@Autowired
	protected SponsorDonationRepository	repository;
	private Donation					donation;

	//AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.donation = this.repository.findDonationByIdAndSponsorUserAccountId(id, userAccountId);

	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);

		status = status && this.donation != null && this.donation.getSponsorship().getDraftMode();

		super.setAuthorised(status);
	}
	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		boolean onlyEuros;

		super.validateObject(this.donation);

		onlyEuros = true;
		if (this.donation.getMoney() != null && this.donation.getMoney().getCurrency() != null)
			onlyEuros = "EUR".equalsIgnoreCase(this.donation.getMoney().getCurrency().trim());

		super.state(onlyEuros, "money", "sponsor.donation.form.error.only-euros");
	}

	@Override
	public void execute() {
		this.repository.save(this.donation);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices;
		choices = SelectChoices.from(DonationKind.class, this.donation.getKind());

		tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("sponsorshipId", this.donation.getSponsorship().getId());
		tuple.put("draftMode", this.donation.getSponsorship().getDraftMode());
		tuple.put("kinds", choices);

	}

}
