
package acme.features.any.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsor;

@Service
public class AnySponsorShowService extends AbstractService<Any, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorRepository	repository;

	private Sponsor					sponsor;

	// AbstractService interface ---------------------------------------------- 


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.sponsor = this.repository.findOneSponsorById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.sponsor != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsor, "address", "im", "gold", "userAccount.identity.name", "userAccount.identity.surname", "userAccount.identity.email");
	}

}
