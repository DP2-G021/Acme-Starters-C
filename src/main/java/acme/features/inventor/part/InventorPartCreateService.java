
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.parts.Part;
import acme.entities.parts.PartKind;
import acme.realms.Inventor;

@Service
public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorPartRepository	repository;

	private Inventor				inventor;
	private Invention				invention;
	private Part					part;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int inventionId;
		int userAccountId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (this.inventor == null)
			this.invention = null;
		else
			this.invention = this.repository.findOneInventionByIdAndInventorId(inventionId, this.inventor.getId());

		this.part = super.newObject(Part.class);
		this.part.setName("");
		this.part.setDescription("");
		this.part.setCost(null);
		this.part.setKind(PartKind.CORE);
		this.part.setInvention(this.invention);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.inventor != null && this.inventor.isPrincipal() && this.invention != null && this.invention.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
		this.part.setInvention(this.invention);
	}

	@Override
	public void validate() {
		boolean onlyEuros;

		super.validateObject(this.part);

		onlyEuros = true;
		if (this.part.getCost() != null && this.part.getCost().getCurrency() != null)
			onlyEuros = "EUR".equalsIgnoreCase(this.part.getCost().getCurrency().trim());

		super.state(onlyEuros, "cost", "inventor.part.form.error.only-euros");
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices kinds;

		kinds = SelectChoices.from(PartKind.class, this.part.getKind());

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		tuple.put("inventionId", this.invention == null ? 0 : this.invention.getId());
		tuple.put("inventionDraftMode", this.invention != null && this.invention.getDraftMode());
		tuple.put("kinds", kinds);
	}
}
