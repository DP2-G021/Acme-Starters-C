
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignShowService extends AbstractService<Authenticated, Campaign> {

	// Internal state -------------------------------------------------------

	@Autowired
	protected SpokespersonCampaignRepository	repository;

	private Campaign							campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.campaign = this.repository.findCampaignByIdAndSpokespersonUserAccountId(id, userAccountId);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.campaign = this.repository.findCampaignByIdAndSpokespersonUserAccountId(id, userAccountId);
		status = status && this.campaign != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "effort", "monthsActive");
		tuple.put("draftModeDisplay", SpokespersonCampaignI18nHelper.draftModeDisplay(this.campaign.getDraftMode()));
	}

}
