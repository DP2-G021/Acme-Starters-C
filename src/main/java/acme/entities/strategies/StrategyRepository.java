
package acme.entities.strategies;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface StrategyRepository extends AbstractRepository {

	// CUMPLE CONSTRAINT (Soporte BD): "Strategies cannot be published unless they have at least one tactic."
	// Permite comprobar si existen tácticas asociadas a esta estrategia en la base de datos.
	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	int countTacticsByStrategyId(@Param("strategyId") int strategyId);

	// CUMPLE CONSTRAINT (Soporte BD): "The expected percentage is the sum of the expected percentage of each tactic."
	// Calcula la suma de porcentajes esperados directamente mediante una consulta agregada.
	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double sumExpectedPercentageByStrategyId(@Param("strategyId") int strategyId);

	// CUMPLE CONSTRAINT (Soporte BD): evitar excepción por ticker duplicado (unique=true).
	// Comprueba si existe otra Strategy con el mismo ticker (excluyendo la de id dado).
	@Query("select count(s) > 0 from Strategy s where s.ticker = :ticker and s.id <> :id")
	boolean existsOtherByTicker(@Param("id") int id, @Param("ticker") String ticker);

}
