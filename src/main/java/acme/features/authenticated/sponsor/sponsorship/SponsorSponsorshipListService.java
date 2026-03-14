
package acme.features.authenticated.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipListService extends AbstractService<Authenticated, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorSponsorshipRepository	repository;

	private Collection<Sponsorship>			sponsorships;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.sponsorships = this.repository.findSponsorshipsBySponsorUserAccountId(userAccountId);
	}

	@Override
	public void unbind() {
		if (this.sponsorships != null)
			for (final Sponsorship sponsorship : this.sponsorships)
				super.unbindObject(sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "draftMode");
	}
}
