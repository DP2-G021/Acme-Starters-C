
package acme.features.authenticated.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.DonationKind;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationCreateService extends AbstractService<Authenticated, Donation> {

	//Internal state ---------------------------------------------------------
	@Autowired
	protected SponsorDonationRepository	repository;
	private Donation					donation;

	//AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int sponsorshipId;
		int userAccountId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		sponsorship = this.repository.findSponsorshipByIdAndSponsorUserAccountId(sponsorshipId, userAccountId);

		this.donation = super.newObject(Donation.class);
		this.donation.setName("");
		this.donation.setNotes("");
		this.donation.setMoney(null);
		this.donation.setKind(DonationKind.ALTRUIST);
		this.donation.setSponsorship(sponsorship);

	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);
		status = status && this.donation.getSponsorship() != null && this.donation.getSponsorship().getDraftMode();

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
