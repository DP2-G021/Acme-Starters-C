
package acme.features.authenticated.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.entities.auditreports.AuditSection;
import acme.helpers.RequestDataHelper;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportDeleteService extends AbstractService<Authenticated, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Integer id;
		int userAccountId;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.auditReport = null;
		else {
			userAccountId = super.getRequest().getPrincipal().getAccountId();
			this.auditReport = this.repository.findAuditReportByIdAndAuditorUserAccountId(id, userAccountId);
		}
	}
	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		status = status && this.auditReport != null && this.auditReport.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		Collection<AuditSection> auditSections;

		auditSections = this.repository.findAuditSectionsByAuditReportId(this.auditReport.getId());
		this.repository.deleteAll(auditSections);
		this.repository.delete(this.auditReport);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours");
		tuple.put("draftModeDisplay", AuditorAuditReportI18nHelper.draftModeDisplay(this.auditReport.getDraftMode()));
	}

}
