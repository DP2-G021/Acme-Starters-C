
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.parts.Part;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPartRepository	repository;

	private Part				part;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPublishedPartById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.part, "name", "description", "cost", "kind");

	}

}
