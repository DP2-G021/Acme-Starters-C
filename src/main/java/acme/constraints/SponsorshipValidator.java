
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipRepository;

public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	@Autowired
	private SponsorshipRepository repository;


	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		assert context != null;

		if (sponsorship == null)
			return true;

		boolean ok = true;

		if (!sponsorship.getDraftMode()) {

			// 1. Validación de intervalo temporal
			boolean datesAreCorrect = false;

			if (sponsorship.getStartMoment() != null && sponsorship.getEndMoment() != null)
				datesAreCorrect = sponsorship.getEndMoment().compareTo(sponsorship.getStartMoment()) > 0;

			super.state(context, datesAreCorrect, "endMoment", "startMoment/endMoment must be a valid time interval");

			// 2. Validación de existencia de donaciones
			int totalDonations = this.repository.findDonationsSizeBySponsorshipId(sponsorship.getId());

			boolean hasAtLeastOne = totalDonations > 0;

			super.state(context, hasAtLeastOne, "donations", "Sponsorships cannot be published unless they have at least one donation");
		}

		ok = !super.hasErrors(context);
		return ok;

	}

}
