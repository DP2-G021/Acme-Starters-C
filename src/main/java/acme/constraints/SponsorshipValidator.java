
package acme.constraints;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.entities.sponsorships.Donation;
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

		if (!sponsorship.getDraftMode()) {
			// 1. Validamos intervalo: startMoment/endMoment must be a valid time interval
			boolean intervalValid = false;
			if (sponsorship.getStartMoment() != null && sponsorship.getEndMoment() != null)
				intervalValid = sponsorship.getEndMoment().after(sponsorship.getStartMoment());

			super.state(context, intervalValid, "endMoment", "startMoment/endMoment must be a valid time interval");

			// 2. Validamos donaciones: Sponsorships cannot be published unless they have at least one donation
			boolean hasDonation = false;
			if (sponsorship.getId() != 0) {
				List<Donation> donations = this.repository.findDonationBySponsorshipId(sponsorship.getId());
				hasDonation = donations != null && !donations.isEmpty();
			}

			super.state(context, hasDonation, "donations", "Sponsorships cannot be published unless they have at least one donation");
		}

		return !super.hasErrors(context);
	}

}
