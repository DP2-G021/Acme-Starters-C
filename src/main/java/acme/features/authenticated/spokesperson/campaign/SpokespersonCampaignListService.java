
package acme.features.authenticated.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignListService extends AbstractService<Authenticated, Campaign> {

	// Internal state ------------------------------------------------------

	@Autowired
	protected SpokespersonCampaignRepository	repository;

	private Collection<Campaign>				campaigns;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int spokespersonId;

		spokespersonId = super.getRequest().getPrincipal().getAccountId();
		this.campaigns = this.repository.findCampaignsBySpokespersonUserAccountId(spokespersonId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		if (this.campaigns != null)
			for (final Campaign campaign : this.campaigns) {
				Tuple tuple;
				tuple = super.unbindObject(campaign, "ticker", "name", "startMoment", "endMoment", "draftMode");
				tuple.put("draftModeDisplay", SpokespersonCampaignI18nHelper.draftModeDisplay(campaign.getDraftMode()));
			}

	}
}
