
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Authenticated, Campaign> {

	// Internal state ---------------------------------------------------------

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

		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		status = status && this.campaign != null && this.campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		boolean previousDraftMode;

		previousDraftMode = this.campaign.getDraftMode();
		this.campaign.setDraftMode(false);
		super.validateObject(this.campaign);
		this.campaign.setDraftMode(previousDraftMode);
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "effort", "monthsActive");
	}
}
