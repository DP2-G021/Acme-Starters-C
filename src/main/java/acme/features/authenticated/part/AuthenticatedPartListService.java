
package acme.features.authenticated.part;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.parts.Part;
import acme.realms.Inventor;

@Service
public class AuthenticatedPartListService extends AbstractService<Authenticated, Part> {

	@Autowired
	private AuthenticatedPartRepository	repository;

	private Collection<Part>			parts;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("masterId", int.class);
		this.parts = this.repository.findPartsByInventionId(inventionId);

		if (this.parts == null)
			this.parts = Collections.emptyList();
	}

	@Override
	public void authorise() {
		boolean status;
		int inventionId;
		int userAccountId;
		Inventor inventor;
		Invention invention;

		inventionId = super.getRequest().getData("masterId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (inventor == null)
			status = false;
		else {
			invention = this.repository.findOneInventionByIdAndInventorId(inventionId, inventor.getId());
			status = invention != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
	}

}
