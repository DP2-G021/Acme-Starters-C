
package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.helpers.RequestDataHelper;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyTacticRepository	repository;

	private Collection<Tactic>		tactics;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Integer strategyId;
		Strategy strategy;

		strategyId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "strategyId");
		if (strategyId == null)
			status = false;
		else {
			strategy = this.repository.findPublishedStrategyById(strategyId);
			status = strategy != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer strategyId;

		strategyId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "strategyId");
		if (strategyId == null)
			this.tactics = null;
		else
			this.tactics = this.repository.findTacticsByPublishedStrategyId(strategyId);
	}

	@Override
	public void unbind() {
		if (this.tactics != null)
			for (final Tactic tactic : this.tactics)
				super.unbindObject(tactic, "name", "kind", "expectedPercentage");
	}
}
