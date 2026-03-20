package acme.features.authenticated.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyCreateService extends AbstractService<Authenticated, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected FundraiserStrategyRepository	repository;

	private Strategy						strategy;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		Fundraiser fundraiser;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		fundraiser = this.repository.findFundraiserByUserAccountId(userAccountId);

		this.strategy = super.newObject(Strategy.class);
		this.strategy.setFundraiser(fundraiser);
		this.strategy.setDraftMode(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class) && this.strategy.getFundraiser() != null;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);
	}

	@Override
	public void execute() {
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "expectedPercentage");
		tuple.put("draftModeDisplay", FundraiserStrategyI18nHelper.draftModeDisplay(this.strategy.getDraftMode()));
	}
}
