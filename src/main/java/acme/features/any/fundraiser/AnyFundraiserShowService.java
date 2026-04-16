
package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.helpers.RequestDataHelper;
import acme.realms.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyFundraiserRepository	repository;

	private Fundraiser					fundraiser;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Integer strategyId;

		strategyId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "strategyId");
		if (strategyId == null) {
			this.fundraiser = null;
			status = false;
		} else {
			this.fundraiser = this.repository.findFundraiserByPublishedStrategyId(strategyId);
			status = this.fundraiser != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer strategyId;

		strategyId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "strategyId");
		if (strategyId == null)
			this.fundraiser = null;
		else
			this.fundraiser = this.repository.findFundraiserByPublishedStrategyId(strategyId);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "identity", "bank", "statement", "agent");
	}
}
