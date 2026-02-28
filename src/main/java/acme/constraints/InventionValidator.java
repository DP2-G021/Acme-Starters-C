
package acme.constraints;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionRepository;
import acme.entities.parts.PartRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository	repository;
	@Autowired
	private PartRepository		partRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidInvention invention) {
		assert invention != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (invention == null)
			result = true;
		else {
			{
				boolean uniqueTicker;
				Invention existingInvention;

				existingInvention = this.repository.findInventionByTicker(invention.getTicker());
				uniqueTicker = existingInvention == null || existingInvention.equals(invention);

				super.state(context, uniqueTicker, "ticker", "acme.validation.invention.duplicated-ticker.message");
			}
			{
				boolean hasAtLeastOnePart;

				//getDraftMode means if it is going to be submit or not
				if (invention.getDraftMode())
					hasAtLeastOnePart = true;
				else {
					Long partsCount = this.repository.countPartsByInventionId(invention.getId());
					hasAtLeastOnePart = partsCount != null && partsCount >= 1;
				}

				super.state(context, hasAtLeastOnePart, "*", "acme.validation.invention.at-least-one-part.message");
			}

			// If it is not a draft
			{
				boolean validInterval = true;
				Date start = invention.getStartMoment();
				Date end = invention.getEndMoment();

				if (start != null && end != null) {

					boolean positiveInterval = MomentHelper.isBefore(start, end);
					boolean futureStart = MomentHelper.isAfter(start, MomentHelper.getCurrentMoment()); //it returns the current moment of the framework in develop and also in testing, but not in the deploy since it returns the time of system.now()

					validInterval = positiveInterval && futureStart; //futureStart redundant because of ValidMoment in the entity?
				}

				super.state(context, validInterval, "startMoment", "acme.validation.invention.invalid-interval.message");
			}
			{
				boolean onlyEuros;

				if (invention.getId() == 0)
					onlyEuros = true;

				else {
					Set<String> currencies = this.partRepository.getCurrencyOfAllCost(invention.getId());

					onlyEuros = currencies.isEmpty() || currencies.size() == 1 && currencies.contains("EURO");

				}

				super.state(context, onlyEuros, "cost", "acme.validation.invention.only-euros.message");
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
