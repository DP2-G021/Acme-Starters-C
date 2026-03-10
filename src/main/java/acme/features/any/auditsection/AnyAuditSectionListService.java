
package acme.features.any.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.entities.auditreports.AuditSection;

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
		int auditReportId;
		AuditReport auditReport;

		auditReportId = super.getRequest().getData("auditreportId", int.class);
		auditReport = this.repository.findPublishedAuditReportById(auditReportId);
		status = auditReport != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("auditreportId", int.class);
		this.auditSections = this.repository.findAuditSectionsByPublishedAuditReportId(id);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "hours", "kind");
	}

}
