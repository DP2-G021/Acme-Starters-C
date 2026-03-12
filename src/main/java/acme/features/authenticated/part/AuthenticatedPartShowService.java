
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

	@Autowired
	private AuthenticatedPartRepository	repository;

	private Inventor					inventor;
	private Part						part;


	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (this.inventor == null)
			this.part = null;
		else
			this.part = this.repository.findOnePartByIdAndInventorId(id, this.inventor.getId());
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind", "id");
		tuple.put("inventionId", this.part.getInvention().getId());
	}

}
