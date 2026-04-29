
package acme.features.any.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.entities.auditreports.AuditSection;
import acme.helpers.RequestDataHelper;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	// Internal state

	@Autowired
	private AnyAuditSectionRepository	repository;

	private Collection<AuditSection>	auditSections;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		Integer auditReportId;
		AuditReport auditReport;

		auditReportId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "auditreportId");
		if (auditReportId == null)
			status = false;
		else {
			auditReport = this.repository.findPublishedAuditReportById(auditReportId);
			status = auditReport != null;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer auditReportId;

		auditReportId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "auditreportId");
		if (auditReportId == null)
			this.auditSections = null;
		else
			this.auditSections = this.repository.findAuditSectionsByPublishedAuditReportId(auditReportId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "hours", "kind");
	}

}
