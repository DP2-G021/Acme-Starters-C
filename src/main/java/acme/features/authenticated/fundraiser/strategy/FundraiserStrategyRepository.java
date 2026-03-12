package acme.features.authenticated.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Repository
public interface FundraiserStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.fundraiser.userAccount.id = :userAccountId")
	Collection<Strategy> findStrategiesByFundraiserUserAccountId(int userAccountId);

	@Query("select s from Strategy s where s.id = :id and s.fundraiser.userAccount.id = :userAccountId")
	Strategy findStrategyByIdAndFundraiserUserAccountId(int id, int userAccountId);

	@Query("select f from Fundraiser f where f.userAccount.id = :userAccountId")
	Fundraiser findFundraiserByUserAccountId(int userAccountId);

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> findTacticsByStrategyId(int strategyId);
}
