
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;

@Service
public class AnyStrategyListService extends AbstractService<Any, Strategy> {

	@Autowired
	protected AnyStrategyRepository	repository;

	private Collection<Strategy>		strategies;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.strategies = this.repository.findPublishedStrategies();
	}

	@Override
	public void unbind() {
		if (this.strategies != null)
			for (final Strategy strategy : this.strategies)
				super.unbindObject(strategy, "ticker", "name", "startMoment", "endMoment");
	}
}
