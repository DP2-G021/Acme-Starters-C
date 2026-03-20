
package acme.features.authenticated.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyListService extends AbstractService<Authenticated, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected FundraiserStrategyRepository	repository;

	private Collection<Strategy>			strategies;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class);
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.strategies = this.repository.findStrategiesByFundraiserUserAccountId(userAccountId);
	}

	@Override
	public void unbind() {
		if (this.strategies != null)
			for (final Strategy strategy : this.strategies) {
				Tuple tuple;

				tuple = super.unbindObject(strategy, "ticker", "name", "startMoment", "endMoment", "draftMode");
				tuple.put("draftModeDisplay", FundraiserStrategyI18nHelper.draftModeDisplay(strategy.getDraftMode()));
			}
	}
}
