package acme.features.authenticated.fundraiser.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface FundraiserTacticRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.id = :strategyId and s.fundraiser.userAccount.id = :userAccountId")
	Strategy findStrategyByIdAndFundraiserUserAccountId(int strategyId, int userAccountId);

	@Query("select t from Tactic t where t.strategy.id = :strategyId and t.strategy.fundraiser.userAccount.id = :userAccountId")
	Collection<Tactic> findTacticsByStrategyIdAndFundraiserUserAccountId(int strategyId, int userAccountId);

	@Query("select t from Tactic t where t.id = :id and t.strategy.fundraiser.userAccount.id = :userAccountId")
	Tactic findTacticByIdAndFundraiserUserAccountId(int id, int userAccountId);

}
