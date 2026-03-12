
package acme.features.authenticated.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneShowService extends AbstractService<Authenticated, Milestone> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SpokespersonMilestoneRepository	repository;

	private Milestone							milestone;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.milestone = this.repository.findMilestoneByIdAndSpokespersonUserAccountId(id, userAccountId);
		status = status && this.milestone != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.milestone = this.repository.findMilestoneByIdAndSpokespersonUserAccountId(id, userAccountId);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

}
