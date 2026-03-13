
package acme.features.authenticated.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignUpdateService extends AbstractService<Authenticated, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SpokespersonCampaignRepository	repository;

	private Campaign							campaign;

	// AbstractService interface ----------------------------------------------


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

		// Debe ser portavoz, la campaña debe existir y estar en modo BORRADOR
		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		status = status && this.campaign != null && this.campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// Campos que el usuario puede editar en el formulario
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
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "effort", "monthsActive");
	}

}
