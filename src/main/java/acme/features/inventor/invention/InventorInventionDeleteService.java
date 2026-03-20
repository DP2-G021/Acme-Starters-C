
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.parts.Part;
import acme.realms.Inventor;

@Service
public class InventorInventionDeleteService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;
	private Inventor					inventor;

	// AbstractService interface ----------------------------------------------


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

		status = this.invention != null && this.invention.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {

	}

	@Override
	public void validate() {

	}

	@Override
	public void execute() {
		Collection<Part> parts;

		parts = this.repository.findManyPartsByInventionId(this.invention.getId());

		for (Part part : parts)
			this.repository.delete(part);

		this.repository.delete(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode");
		tuple.put("inventionId", this.invention.getId());
	}
}
