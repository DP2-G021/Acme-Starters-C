
package acme.features.any.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;
import acme.helpers.RequestDataHelper;

@Service
public class AnyTacticShowService extends AbstractService<Any, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyTacticRepository	repository;

	private Tactic					tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Integer id;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null) {
			this.tactic = null;
			status = false;
		} else {
			this.tactic = this.repository.findPublishedTacticById(id);
			status = this.tactic != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer id;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.tactic = null;
		else
			this.tactic = this.repository.findPublishedTacticById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}
}
