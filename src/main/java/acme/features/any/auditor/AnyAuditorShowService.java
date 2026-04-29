
package acme.features.any.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.helpers.RequestDataHelper;
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
		Integer auditreportId;

		auditreportId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "auditreportId");
		if (auditreportId == null) {
			this.auditor = null;
			status = false;
		} else {
			this.auditor = this.repository.findAuditorByPublishedAuditReportId(auditreportId);
			status = this.auditor != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer auditreportId;

		auditreportId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "auditreportId");
		if (auditreportId == null)
			this.auditor = null;
		else
			this.auditor = this.repository.findAuditorByPublishedAuditReportId(auditreportId);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditor, "identity", "firm", "highlights", "solicitor");
	}
}
