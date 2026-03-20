
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CampaignRepository campaignRepository;

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
			/*
			 * {
			 * boolean validInterval;
			 * final Date start = campaign.getStartMoment();
			 * final Date end = campaign.getEndMoment();
			 * 
			 * if (start == null || end == null)
			 * validInterval = true; // El @Mandatory de la entidad ya se encarga de que no sean nulos
			 * else if (MomentHelper.isBefore(start, end))
			 * validInterval = true;
			 * else
			 * validInterval = false;
			 * 
			 * super.state(context, validInterval, "startMoment", "acme.validation.campaign.invalid-interval.message");
			 * }
			 */
			{
				// 1. Obtener los datos (igual que tu compañero)
				boolean validInterval = true;
				boolean validStart = true;
				Date start = campaign.getStartMoment();
				Date end = campaign.getEndMoment();

				if (start != null && end != null) {
					// 2. Lógica de intervalo positivo (Inicio < Fin)
					boolean positiveInterval = MomentHelper.isBefore(start, end);

					// 3. Lógica de fecha futura respecto al sistema (Base Moment: 2025/01/01 00:00)
					// Como isAfter es ESTRICTO, las 00:00 darán error y las 00:01 serán válidas.
					boolean futureStart = MomentHelper.isAfter(start, MomentHelper.getBaseMoment());

					validInterval = positiveInterval;
					validStart = futureStart;
				}

				// 4. Lanzar los errores en los campos correspondientes
				super.state(context, validInterval, "endMoment", "acme.validation.campaign.invalid-interval.message");
				super.state(context, validStart, "startMoment", "acme.validation.campaign.invalid-start.message");
			}

			// El resultado final es true solo si no se ha registrado ningún error en los bloques anteriores
			result = !super.hasErrors(context);
		}

		return result;
	}

}
