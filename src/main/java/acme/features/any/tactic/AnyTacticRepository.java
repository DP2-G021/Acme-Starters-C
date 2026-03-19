package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface AnyTacticRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.id = :id and s.draftMode = false")
	Strategy findPublishedStrategyById(int id);

	@Query("select t from Tactic t where t.strategy.id = :strategyId and t.strategy.draftMode = false")
	Collection<Tactic> findTacticsByPublishedStrategyId(int strategyId);

	@Query("select t from Tactic t where t.id = :id and t.strategy.draftMode = false")
	Tactic findPublishedTacticById(int id);

}
