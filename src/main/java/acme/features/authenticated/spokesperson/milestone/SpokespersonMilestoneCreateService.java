
package acme.features.authenticated.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.entities.campaigns.MilestoneKind;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneCreateService extends AbstractService<Authenticated, Milestone> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SpokespersonMilestoneRepository	repository;

	private Milestone							milestone;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int campaignId;
		int userAccountId;
		Campaign campaign;

		campaignId = super.getRequest().getData("campaignId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		campaign = this.repository.findCampaignByIdAndSpokespersonUserAccountId(campaignId, userAccountId);

		this.milestone = super.newObject(Milestone.class);
		this.milestone.setCampaign(campaign);
		this.milestone.setTitle("");
		this.milestone.setAchievements("");
		this.milestone.setEffort(0.0);
		this.milestone.setKind(MilestoneKind.TEASER);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		status = status && this.milestone.getCampaign() != null && this.milestone.getCampaign().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {
		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		// desplegable
		choices = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());

		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		tuple.put("campaignId", this.milestone.getCampaign().getId());
		tuple.put("draftMode", this.milestone.getCampaign().getDraftMode());
		tuple.put("kinds", choices);
	}
}
