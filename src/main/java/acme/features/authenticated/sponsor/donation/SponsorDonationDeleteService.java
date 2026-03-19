
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
public class SponsorDonationDeleteService extends AbstractService<Authenticated, Donation> {

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

		// 1. Comprobamos rol
		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);

		// 2. Comprobamos que la donación se haya cargado 
		status = status && this.donation != null;

		// 3. Comprobamos la relación de forma segura
		if (status)
			// Si el framework ha limpiado la relación en el POST, 
			// la recargamos de la BD para que el authorise no falle.
			if (this.donation.getSponsorship() == null) {
				// Esto "fuerza" a cargar el objeto real si el formulario lo puso a null
				Donation dbDonation = this.repository.findOneDonation(this.donation.getId());
				status = dbDonation.getSponsorship().getDraftMode();
			} else
				status = this.donation.getSponsorship().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.donation);
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
