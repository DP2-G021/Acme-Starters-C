
package acme.features.authenticated.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.auditreports.AuditSection;
import acme.entities.auditreports.SectionKind;
import acme.helpers.RequestDataHelper;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionUpdateService extends AbstractService<Authenticated, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		Integer id;
		int userAccountId;

		id = RequestDataHelper.getNaturalIntegerParameter(super.getRequest(), "id");
		if (id == null)
			this.auditSection = null;
		else {
			userAccountId = super.getRequest().getPrincipal().getAccountId();
			this.auditSection = this.repository.findAuditSectionByIdAndAuditorUserAccountId(id, userAccountId);
		}
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		status = status && this.auditSection != null && this.auditSection.getAuditReport().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("auditreportId", this.auditSection.getAuditReport().getId());
		tuple.put("draftMode", this.auditSection.getAuditReport().getDraftMode());
		tuple.put("kinds", choices);
	}

}
