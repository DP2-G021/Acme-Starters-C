package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private StrategyRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidStrategy constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {

			// 1. startMoment < endMoment (strict)
			{
				boolean validInterval = true;

				if (strategy.getStartMoment() != null && strategy.getEndMoment() != null)
					validInterval = strategy.getEndMoment().after(strategy.getStartMoment());

				super.state(context, validInterval, "endMoment", "acme.validation.strategy.invalid-interval.message");
			}

			// 2. unique ticker
			{
				boolean uniqueTicker;
				uniqueTicker = !this.repository.existsOtherByTicker(strategy.getId(), strategy.getTicker());

				super.state(context, uniqueTicker, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}

			// 3. publish-only rules
			if (Boolean.FALSE.equals(strategy.getDraftMode())) {

				// 3.1 start and end moments are mandatory to publish
				{
					boolean hasStartMoment;
					boolean hasEndMoment;

					hasStartMoment = strategy.getStartMoment() != null;
					hasEndMoment = strategy.getEndMoment() != null;

					super.state(context, hasStartMoment, "startMoment", "acme.validation.strategy.start-required-to-publish.message");
					super.state(context, hasEndMoment, "endMoment", "acme.validation.strategy.end-required-to-publish.message");
				}

				// 3.2 must have at least one tactic
				{
					boolean hasAtLeastOneTactic;
					int tactics = this.repository.countTacticsByStrategyId(strategy.getId());
					hasAtLeastOneTactic = tactics >= 1;

					super.state(context, hasAtLeastOneTactic, "*", "acme.validation.strategy.at-least-one-tactic.message");
				}

				// 3.3 start and end must be future moments
				{
					boolean futureStart = true;
					boolean futureEnd = true;

					if (strategy.getStartMoment() != null)
						futureStart = MomentHelper.isFuture(strategy.getStartMoment());

					if (strategy.getEndMoment() != null)
						futureEnd = MomentHelper.isFuture(strategy.getEndMoment());

					super.state(context, futureStart, "startMoment", "acme.validation.strategy.start-must-be-future.message");
					super.state(context, futureEnd, "endMoment", "acme.validation.strategy.end-must-be-future.message");
				}
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
