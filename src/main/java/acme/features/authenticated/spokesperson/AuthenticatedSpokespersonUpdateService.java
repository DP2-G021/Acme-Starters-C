
package acme.features.authenticated.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AuthenticatedSpokespersonUpdateService extends AbstractService<Authenticated, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedSpokespersonRepository	repository;

	private Spokesperson							spokesperson;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.spokesperson = this.repository.findSpokespersonByUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class));
	}

	@Override
	public void bind() {
		super.bindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.spokesperson);
	}

	@Override
	public void execute() {
		this.repository.save(this.spokesperson);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
