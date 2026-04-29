
package acme.features.any.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.helpers.RequestDataHelper;

@Service
public class AnyAuditReportsShowService extends AbstractService<Any, AuditReport> {

	// Internal state

	@Autowired
	private AnyAuditReportsRepository	repository;

	private AuditReport					auditReport;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		Integer id;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null) {
			this.auditReport = null;
			status = false;
		} else {
			this.auditReport = this.repository.findPublishedAuditReportBy(id);
			status = this.auditReport != null;
		}

		super.setAuthorised(status);
	}
	@Override
	public void load() {
		Integer id;
		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.auditReport = null;
		else
			this.auditReport = this.repository.findPublishedAuditReportBy(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours");
	}
}
