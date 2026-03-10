
package acme.features.any.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditReport;

@Service
public class AnyAuditReportsListService extends AbstractService<Any, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditReportsRepository	repository;

	private Collection<AuditReport>		auditreports;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.auditreports = this.repository.findAllPublishedAuditReports();
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditreports, "ticker", "name", "startMoment", "endMoment");
	}

}
