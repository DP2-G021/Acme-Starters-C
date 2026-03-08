
package acme.features.any.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;

@Service
public class AnyStrategyShowService extends AbstractService<Any, Strategy> {

	@Autowired
	protected AnyStrategyRepository	repository;

	private Strategy				strategy;


	@Override
	public void authorise() {
		boolean status;
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findPublishedStrategyById(id);
		status = this.strategy != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findPublishedStrategyById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage");
	}
}
