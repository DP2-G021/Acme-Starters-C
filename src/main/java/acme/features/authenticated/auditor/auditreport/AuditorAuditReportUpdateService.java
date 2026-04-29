
package acme.features.authenticated.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.helpers.RequestDataHelper;

@Service
public class AuditorAuditReportUpdateService extends AbstractService<Authenticated, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface ----------------------------------------------


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

		status = this.auditReport != null && this.auditReport.getDraftMode();

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

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "draftMode");
		tuple.put("auditReportId", this.auditReport.getId());
		tuple.put("draftModeDisplay", AuditorAuditReportI18nHelper.draftModeDisplay(this.auditReport.getDraftMode()));
	}

}
