
package acme.features.authenticated.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportListService extends AbstractService<Authenticated, AuditReport> {

	// Internal state

	@Autowired
	protected AuditorAuditReportRepository	repository;

	private Collection<AuditReport>			auditReports;

	// AbstractService interface


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditReports = this.repository.findAuditReportsByAuditorUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		if (this.auditReports != null)
			for (final AuditReport a : this.auditReports) {
				Tuple tuple = super.unbindObject(a, "ticker", "name", "draftMode");
				tuple.put("draftModeDisplay", AuditorAuditReportI18nHelper.draftModeDisplay(a.getDraftMode()));
			}
	}
}
