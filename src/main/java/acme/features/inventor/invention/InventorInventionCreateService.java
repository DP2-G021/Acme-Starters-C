
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionCreateService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;
	private Inventor					inventor;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		this.invention = super.newObject(Invention.class);
		this.invention.setTicker("");
		this.invention.setName("");
		this.invention.setDescription("");
		this.invention.setStartMoment(null);
		this.invention.setEndMoment(null);
		this.invention.setMoreInfo("");
		this.invention.setDraftMode(true);
		this.invention.setInventor(this.inventor);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.inventor != null && this.inventor.isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode");
		tuple.put("inventorId", this.inventor == null ? 0 : this.inventor.getId());
	}
}
