
package acme.features.authenticated.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionShowService extends AbstractService<Authenticated, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int userAccountId;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditSection = this.repository.findAuditSectionByIdAndAuditorUserAccountId(id, userAccountId);
		status = status && this.auditSection != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.auditSection = this.repository.findAuditSectionByIdAndAuditorUserAccountId(id, userAccountId);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

}
