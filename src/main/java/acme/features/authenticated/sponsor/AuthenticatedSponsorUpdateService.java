
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AuthenticatedSponsorUpdateService extends AbstractService<Authenticated, Sponsor> {

	@Autowired
	protected AuthenticatedSponsorRepository	repository;

	private Sponsor								sponsor;


	@Override
	public void load() {
		int userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.sponsor = this.repository.findSponsorByUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class));
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsor, "address", "im", "gold");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsor);
	}

	@Override
	public void execute() {
		this.repository.save(this.sponsor);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsor, "address", "im", "gold");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
