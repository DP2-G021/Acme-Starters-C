
package acme.features.any.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditSection;
import acme.helpers.RequestDataHelper;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	// Internal state

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				auditSection;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		Integer id;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null) {
			this.auditSection = null;
			status = false;
		} else {
			this.auditSection = this.repository.findPublishedAuditSectionById(id);
			status = this.auditSection != null;
		}

		super.setAuthorised(status);
	}
	@Override
	public void load() {
		Integer id;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.auditSection = null;
		else
			this.auditSection = this.repository.findPublishedAuditSectionById(id);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
	}
}
