
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.CampaignRepository;
import acme.entities.campaigns.MilestoneRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CampaignRepository	campaignRepository;
	@Autowired
	private MilestoneRepository	milestoneRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidCampaign campaign) {
		assert campaign != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result = true;

		if (campaign == null)
			result = true;
		else {
			// 1. Validación de Ticker Único
			{
				boolean uniqueTicker;
				Campaign existingCampaign;

				existingCampaign = this.campaignRepository.findCampaignByTicker(campaign.getTicker());

				if (existingCampaign == null)
					uniqueTicker = true;
				else
					uniqueTicker = existingCampaign.getId() == campaign.getId();

				super.state(context, uniqueTicker, "ticker", "acme.validation.campaign.duplicated-ticker.message");
			}

			// 2. Validación de hitos mínimos para publicar
			{
				boolean hasAtLeastOneMilestone;

				if (campaign.getDraftMode() == null || campaign.getDraftMode())
					// Si es borrador, no es obligatorio tener hitos aún
					hasAtLeastOneMilestone = true;
				else {
					// Si NO es borrador (se publica), hay que contar en la base de datos
					Long milestonesCount = this.campaignRepository.countMilestonesByCampaignId(campaign.getId());

					if (milestonesCount != null && milestonesCount >= 1L)
						hasAtLeastOneMilestone = true;
					else
						hasAtLeastOneMilestone = false;
				}

				super.state(context, hasAtLeastOneMilestone, "*", "acme.validation.campaign.at-least-one-milestone.message");
			}

			// 3. Validación de intervalo de fechas
			{
				boolean validInterval;
				final Date start = campaign.getStartMoment();
				final Date end = campaign.getEndMoment();

				if (start == null || end == null)
					validInterval = true; // El @Mandatory de la entidad ya se encarga de que no sean nulos
				else if (MomentHelper.isBefore(start, end))
					validInterval = true;
				else
					validInterval = false;

				super.state(context, validInterval, "startMoment", "acme.validation.campaign.invalid-interval.message");
			}

			// 4. Validación de esfuerzo total (No superar el 100%)
			{
				boolean validEffort;
				Double totalEffort = this.milestoneRepository.getCampaignEffort(campaign.getId());

				if (totalEffort == null)
					validEffort = true;
				else if (totalEffort <= 100.0)
					validEffort = true;
				else
					validEffort = false;

				super.state(context, validEffort, "effort", "acme.validation.campaign.effort-exceeded.message");
			}

			// El resultado final es true solo si no se ha registrado ningún error en los bloques anteriores
			result = !super.hasErrors(context);
		}

		return result;
	}

}
