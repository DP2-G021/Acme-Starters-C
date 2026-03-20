
package acme.features.authenticated.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Authenticated, Sponsorship> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		// 1. Comprobamos que el usuario logueado tenga el rol de Sponsor
		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);

		// 2. Verificamos que el sponsor del objeto coincida con el usuario actual
		if (status && this.sponsorship != null && this.sponsorship.getSponsor() != null) {
			int userAccountId = super.getRequest().getPrincipal().getAccountId();
			status = this.sponsorship.getSponsor().getUserAccount().getId() == userAccountId;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int userAccountId;
		Sponsor sponsor;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		sponsor = this.repository.findSponsorByUserAccountId(userAccountId);

		this.sponsorship = super.newObject(Sponsorship.class);
		this.sponsorship.setSponsor(sponsor);
		this.sponsorship.setDraftMode(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
	}

	@Override
	public void execute() {
		this.repository.save(this.sponsorship);
	}
	@Override
	public void unbind() {
		Tuple tuple;
		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney", "draftMode");
		tuple.put("draftModeDisplay", SponsorSponsorshipI18nHelper.draftModeDisplay(this.sponsorship.getDraftMode()));
	}

}
