
package acme.features.authenticated.invention;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class AuthenticatedInventionListService extends AbstractService<Authenticated, Invention> {

	@Autowired
	private AuthenticatedInventionRepository	repository;

	private Collection<Invention>				inventions;


	@Override
	public void load() {
		int userAccountId;
		Inventor inventor;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (inventor == null)
			this.inventions = Collections.emptyList();
		else
			this.inventions = this.repository.findInventionsByInventorId(inventor.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		int userAccountId;
		Inventor inventor;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		status = inventor != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "monthsActive", "cost", "moreInfo");
	}

}
