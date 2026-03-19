
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipRepository;

public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	@Autowired
	private SponsorshipRepository repository;


	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;

		if (sponsorship == null)
			return true;
		else {

			// 1. Validación de ticker único
			{
				Sponsorship existingSponsorship = this.repository.findSponsorshipByTicker(sponsorship.getTicker());
				boolean uniqueSponsorship = existingSponsorship == null || existingSponsorship.getId() == sponsorship.getId();

				super.state(context, uniqueSponsorship, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");
			}

			// 2. Validación de existencia de donaciones
			{
				boolean hasDonations;

				if (sponsorship.getDraftMode())
					hasDonations = true;
				else {
					int size = this.repository.findDonationsSizeBySponsorshipId(sponsorship.getId());
					hasDonations = size > 0;
				}
				super.state(context, hasDonations, "draftMode", "acme.validation.sponsorship.at-least-one-donation.message");

			}
			{
				// 3. Validación de intervalo temporal
				boolean datesAreCorrect = false;
				if (sponsorship.getStartMoment() != null && sponsorship.getEndMoment() != null)
					datesAreCorrect = MomentHelper.isBefore(sponsorship.getStartMoment(), sponsorship.getEndMoment());

				super.state(context, datesAreCorrect, "endMoment", "acme.validation.sponsorship.invalid-time-interval.message");
			}

			{

				boolean correctCurrency;

				correctCurrency = this.repository.findCurrenciesOfDonationsBySponsorshipId(sponsorship.getId()).stream().allMatch(c -> c.equals("EUR"));

				super.state(context, correctCurrency, "money", "acme.validation.invalid-currency.message");
			}

			result = !super.hasErrors(context);
		}
		return result;

	}

}
