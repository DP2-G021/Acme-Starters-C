
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;

@Component
public class ValidStrategyValidator implements ConstraintValidator<ValidStrategy, Strategy> {

	@Autowired
	private StrategyRepository repository;


	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		if (strategy == null)
			return true;

		boolean ok = true;

		// 1) startMoment < endMoment
		if (strategy.getStartMoment() != null && strategy.getEndMoment() != null)
			if (!MomentHelper.isBefore(strategy.getStartMoment(), strategy.getEndMoment())) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("startMoment must be before endMoment").addPropertyNode("endMoment").addConstraintViolation();
				ok = false;
			}

		// 2) ticker único (evita crash BD)
		if (strategy.getTicker() != null) {
			boolean duplicated = this.repository.existsOtherByTicker(strategy.getId(), strategy.getTicker());
			if (duplicated) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("ticker must be unique").addPropertyNode("ticker").addConstraintViolation();
				ok = false;
			}
		}

		// 3) reglas SOLO si se publica (draftMode=false)
		boolean published = Boolean.FALSE.equals(strategy.getDraftMode()); // draftMode es Boolean en tu Strategy

		if (published) {
			// 3.1) Debe tener al menos una tactic
			int tactics = this.repository.countTacticsByStrategyId(strategy.getId());
			if (tactics < 1) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("cannot publish without at least one tactic").addPropertyNode("draftMode").addConstraintViolation();
				ok = false;
			}

			// 3.2) start/end deben ser futuros con reloj virtual (MomentHelper)
			if (strategy.getStartMoment() != null && !MomentHelper.isFuture(strategy.getStartMoment())) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("startMoment must be in the future when published").addPropertyNode("startMoment").addConstraintViolation();
				ok = false;
			}
			if (strategy.getEndMoment() != null && !MomentHelper.isFuture(strategy.getEndMoment())) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("endMoment must be in the future when published").addPropertyNode("endMoment").addConstraintViolation();
				ok = false;
			}
		}

		return ok;
	}
}
