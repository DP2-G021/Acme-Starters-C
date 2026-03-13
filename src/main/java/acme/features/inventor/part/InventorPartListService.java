
package acme.features.inventor.part;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.parts.Part;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Inventor				inventor;
	private Invention				invention;
	private Collection<Part>		parts;


	@Override
	public void load() {
		int inventionId;
		int userAccountId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (this.inventor == null) {
			this.invention = null; // NUEVO
			this.parts = Collections.emptyList();
		} else {
			this.invention = this.repository.findOneInventionByIdAndInventorId(inventionId, this.inventor.getId()); // NUEVO: valido ownership aquí

			if (this.invention == null)
				this.parts = Collections.emptyList();
			else
				this.parts = this.repository.findPartsByInventionId(inventionId);
		}
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
	}

}
