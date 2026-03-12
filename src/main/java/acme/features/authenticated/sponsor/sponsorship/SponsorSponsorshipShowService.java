
package acme.features.authenticated.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsor;
import acme.entities.sponsorships.Sponsorship;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Authenticated, Sponsorship> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface ------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);
		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.sponsorship = this.repository.findSponsorshipByIdAndSponsorUserAccountId(id, userAccountId);
		status = status && this.sponsorship != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.sponsorship = this.repository.findSponsorshipByIdAndSponsorUserAccountId(id, userAccountId);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "totalMoney");
	}
}
