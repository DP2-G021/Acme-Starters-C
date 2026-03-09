
package acme.features.authenticated.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class AuthenticatedInventionShowService extends AbstractService<Authenticated, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedInventionRepository	repository;

	private Invention							invention;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int userAccountId;
		Inventor inventor;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (inventor == null)
			status = false;
		else {
			this.invention = this.repository.findOneInventionByIdAndInventorId(id, inventor.getId());
			status = this.invention != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		int userAccountId;
		Inventor inventor;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (inventor != null)
			this.invention = this.repository.findOneInventionByIdAndInventorId(id, inventor.getId());
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode", "id");
		tuple.put("inventorId", this.invention.getInventor().getId());
	}

}
