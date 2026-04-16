
package acme.features.authenticated.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;
import acme.entities.strategies.TacticKind;
import acme.helpers.RequestDataHelper;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticShowService extends AbstractService<Authenticated, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected FundraiserTacticRepository	repository;

	private Tactic							tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Integer id;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class);
		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.tactic = null;
		else {
			userAccountId = super.getRequest().getPrincipal().getAccountId();
			this.tactic = this.repository.findTacticByIdAndFundraiserUserAccountId(id, userAccountId);
		}
		status = status && this.tactic != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer id;
		int userAccountId;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.tactic = null;
		else {
			userAccountId = super.getRequest().getPrincipal().getAccountId();
			this.tactic = this.repository.findTacticByIdAndFundraiserUserAccountId(id, userAccountId);
		}
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(TacticKind.class, this.tactic.getKind());

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", this.tactic.getStrategy().getId());
		tuple.put("draftMode", this.tactic.getStrategy().getDraftMode());
		tuple.put("kinds", choices);
	}
}
