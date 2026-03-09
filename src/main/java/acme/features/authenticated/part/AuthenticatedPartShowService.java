
package acme.features.authenticated.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.parts.Part;
import acme.realms.Inventor;

@Service
public class AuthenticatedPartShowService extends AbstractService<Authenticated, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedPartRepository	repository;

	private Part						part;

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
			this.part = this.repository.findOnePartByIdAndInventorId(id, inventor.getId());
			status = this.part != null;
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
			this.part = this.repository.findOnePartByIdAndInventorId(id, inventor.getId());
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind", "id");
		tuple.put("inventionId", this.part.getInvention().getId());
	}

}
