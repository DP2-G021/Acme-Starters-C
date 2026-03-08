
package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {

	@Autowired
	protected AnyTacticRepository	repository;

	private Collection<Tactic>		tactics;


	@Override
	public void authorise() {
		boolean status;
		int strategyId;
		Strategy strategy;

		strategyId = super.getRequest().getData("strategyId", int.class);
		strategy = this.repository.findPublishedStrategyById(strategyId);
		status = strategy != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findTacticsByPublishedStrategyId(strategyId);
	}

	@Override
	public void unbind() {
		if (this.tactics != null)
			for (final Tactic tactic : this.tactics)
				super.unbindObject(tactic, "name", "kind", "expectedPercentage");
	}
}
