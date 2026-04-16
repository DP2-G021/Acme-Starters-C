
package acme.features.any.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.helpers.RequestDataHelper;

@Service
public class AnyStrategyShowService extends AbstractService<Any, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyStrategyRepository	repository;

	private Strategy				strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Integer id;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null) {
			this.strategy = null;
			status = false;
		} else {
			this.strategy = this.repository.findPublishedStrategyById(id);
			status = this.strategy != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer id;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.strategy = null;
		else
			this.strategy = this.repository.findPublishedStrategyById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage");
	}
}
