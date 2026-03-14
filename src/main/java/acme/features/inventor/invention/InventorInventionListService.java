
package acme.features.inventor.invention;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionListService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventorInventionRepository	repository;

	private Inventor					inventor;
	private Collection<Invention>		inventions;


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.inventor = this.repository.findOneInventorByUserAccountId(userAccountId);

		if (this.inventor == null)
			this.inventions = Collections.emptyList();
		else
			this.inventions = this.repository.findManyInventionsByInventorId(this.inventor.getId());
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.inventor != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment", "monthsActive", "cost", "moreInfo", "draftMode");
	}

}
