
package acme.features.authenticated.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportCreateService extends AbstractService<Authenticated, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		Auditor auditor;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		auditor = this.repository.findAuditorByUserAccountId(userAccountId);

		if (auditor == null)
			this.auditReport = null;
		else {
			this.auditReport = super.newObject(AuditReport.class);
			this.auditReport.setAuditor(auditor);
			this.auditReport.setDraftMode(true);
		}
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class) && this.auditReport != null;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours");
		tuple.put("draftModeDisplay", AuditorAuditReportI18nHelper.draftModeDisplay(this.auditReport.getDraftMode()));
	}

}
