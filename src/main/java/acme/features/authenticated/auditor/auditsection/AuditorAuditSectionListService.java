
package acme.features.authenticated.auditor.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.entities.auditreports.AuditSection;
import acme.helpers.RequestDataHelper;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Authenticated, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditSectionRepository	repository;

	private Collection<AuditSection>		auditSections;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Integer auditReportId;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		auditReportId = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "auditreportId");
		if (auditReportId == null)
			this.auditReport = null;
		else {
			userAccountId = super.getRequest().getPrincipal().getAccountId();
			this.auditReport = this.repository.findAuditReportByIdAndAuditorUserAccountId(auditReportId, userAccountId);
		}
		status = status && this.auditReport != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int auditReportId;
		int userAccountId;

		auditReportId = super.getRequest().getData("auditreportId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditReport = this.repository.findAuditReportByIdAndAuditorUserAccountId(auditReportId, userAccountId);
		this.auditSections = this.repository.findAuditSectionsByAuditReportIdAndAuditorUserAccountId(auditReportId, userAccountId);
	}

	@Override
	public void unbind() {
		if (this.auditSections != null)
			for (final AuditSection auditSection : this.auditSections)
				super.unbindObject(auditSection, "name", "hours", "kind");

		super.unbindGlobal("auditreportId", this.auditReport.getId());
		super.unbindGlobal("showCreate", this.auditReport.getDraftMode());
	}
}
