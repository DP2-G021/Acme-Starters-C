
package acme.features.any.campaigns;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	// Internal state ------------------------------------------------------

	@Autowired
	private AnyCampaignRepository	repository;

	private Collection<Campaign>	campaigns;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		// traemos solo las publicadas
		this.campaigns = this.repository.findAnyPublished();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		// campos que queremos mostrar en la tabla
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment");
	}

}
