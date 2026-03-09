
package acme.features.any.campaigns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class AnyCampaignShowService extends AbstractService<Any, Campaign> {

	// Internal state -------------------------------------------------------

	@Autowired
	private AnyCampaignRepository	repository;

	private Campaign				campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		//cualquier usuario puede verlas siempre que estén publicadas
		status = this.campaign != null && !this.campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "effort");

		tuple.put("spokespersonId", this.campaign.getSpokesperson().getId());
		tuple.put("campaignId", this.campaign.getId());
	}

}
