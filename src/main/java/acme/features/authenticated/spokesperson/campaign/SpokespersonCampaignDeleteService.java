
package acme.features.authenticated.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignDeleteService extends AbstractService<Authenticated, Campaign> {

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
		;
	}

	@Override
	public void execute() {
		Collection<Milestone> milestones;

		//borrar milestones antes que las campañas
		milestones = this.repository.findMilestonesByCampaignId(this.campaign.getId());
		this.repository.deleteAll(milestones);
		this.repository.delete(this.campaign);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "effort", "monthsActive");
		tuple.put("draftModeDisplay", SpokespersonCampaignI18nHelper.draftModeDisplay(this.campaign.getDraftMode()));
	}

}
