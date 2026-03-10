
package acme.features.any.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AnyAuditorShowService extends AbstractService<Any, Auditor> {

	// Internal state

	@Autowired
	private AnyAuditorRepository	repository;

	private Auditor					auditor;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int auditReportId;

		auditReportId = super.getRequest().getData("auditreportId", int.class);
		this.auditor = this.repository.findPublishedAuditorByAuditReportId(auditReportId);
		status = this.auditor != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("auditreportId", int.class);
		this.auditor = this.repository.findPublishedAuditorByAuditReportId(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditor, "identity", "firm", "highlights", "solicitor");
	}
}
