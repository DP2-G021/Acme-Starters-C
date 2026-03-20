
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignCreateService extends AbstractService<Authenticated, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SpokespersonCampaignRepository	repository;

	private Campaign							campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		Spokesperson spokesperson;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		spokesperson = this.repository.findSpokespersonByUserAccountId(userAccountId);

		this.campaign = super.newObject(Campaign.class);
		this.campaign.setSpokesperson(spokesperson);
		this.campaign.setDraftMode(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class) && this.campaign.getSpokesperson() != null;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// datos que el usuario PUEDE editar
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		// datos que se muestran
		Tuple tuple;
		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "effort", "monthsActive");
		tuple.put("draftModeDisplay", SpokespersonCampaignI18nHelper.draftModeDisplay(this.campaign.getDraftMode()));

	}

}
