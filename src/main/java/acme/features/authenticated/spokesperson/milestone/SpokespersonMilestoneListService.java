
package acme.features.authenticated.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Authenticated, Milestone> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SpokespersonMilestoneRepository	repository;

	private Collection<Milestone>				milestones;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int campaignId;
		int userAccountId;
		Campaign campaign;

		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		campaignId = super.getRequest().getData("campaignId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		campaign = this.repository.findCampaignByIdAndSpokespersonrUserAccountId(campaignId, userAccountId);
		status = status && campaign != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int campaignId;
		int userAccountId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.milestones = this.repository.findMilestonesByCampaignIdAndSpokespersonUserAccountId(campaignId, userAccountId);
	}

	@Override
	public void unbind() {
		if (this.milestones != null)
			for (final Milestone milestone : this.milestones)
				super.unbindObject(milestone, "title", "effort", "kind");
	}
}
