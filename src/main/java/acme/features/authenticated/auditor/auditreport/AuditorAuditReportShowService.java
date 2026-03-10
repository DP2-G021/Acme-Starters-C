
package acme.features.authenticated.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportShowService extends AbstractService<Authenticated, AuditReport> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditReport = this.repository.findAuditReportByIdAndAuditorUserAccountId(id, userAccountId);
		status = status && this.auditReport != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditReport = this.repository.findAuditReportByIdAndAuditorUserAccountId(id, userAccountId);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours");
	}
}
