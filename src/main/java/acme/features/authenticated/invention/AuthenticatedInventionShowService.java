
package acme.features.authenticated.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class AuthenticatedInventionShowService extends AbstractService<Authenticated, Invention> {

	@Autowired
	private AuthenticatedInventionRepository	repository;

	private Inventor							inventor;
	private Invention							invention;


	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (this.inventor == null)
			this.invention = null;
		else
			this.invention = this.repository.findOneInventionByIdAndInventorId(id, this.inventor.getId());
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null; // CAMBIO: no repito consultas; autorizo solo si load() ha encontrado la invención.

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode", "id");
		// CAMBIO: elimino tuple.put("inventorId", ...) porque en este JSP ya no se usa.
	}

}
