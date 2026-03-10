
package acme.features.authenticated.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticListService extends AbstractService<Authenticated, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected FundraiserTacticRepository	repository;

	private Collection<Tactic>				tactics;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int strategyId;
		int userAccountId;
		Strategy strategy;

		status = super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class);
		strategyId = super.getRequest().getData("strategyId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		strategy = this.repository.findStrategyByIdAndFundraiserUserAccountId(strategyId, userAccountId);
		status = status && strategy != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int strategyId;
		int userAccountId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.tactics = this.repository.findTacticsByStrategyIdAndFundraiserUserAccountId(strategyId, userAccountId);
	}

	@Override
	public void unbind() {
		if (this.tactics != null)
			for (final Tactic tactic : this.tactics)
				super.unbindObject(tactic, "name", "kind", "expectedPercentage");
	}
}
