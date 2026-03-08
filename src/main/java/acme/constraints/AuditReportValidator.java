
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.auditreports.AuditReport;
import acme.entities.auditreports.AuditReportRepository;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	@Autowired
	private AuditReportRepository repository;


	@Override
	public void initialize(final ValidAuditReport constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {

		assert context != null;

		if (auditReport == null)
			return true;

		// Check unique ticker
		if (auditReport.getTicker() != null) {
			AuditReport existing = this.repository.findByTicker(auditReport.getTicker());

			boolean uniqueTicker = existing == null || existing.getId() == auditReport.getId();

			super.state(context, uniqueTicker, "ticker", "acme.validation.auditReport.ticker.non-unique");
		}

		// CUMPLE CONSTRAINT: "Audit reports cannot be published unless they have at least one audit section."
		// If the report is not in draft mode, it must have at least one section
		if (auditReport.getDraftMode() != null && !auditReport.getDraftMode()) {

			Integer auditSectionCount = this.repository.countByAuditReportId(auditReport.getId());
			boolean hasAuditSections = auditSectionCount != null && auditSectionCount >= 1;

			super.state(context, hasAuditSections, "draftMode", "acme.validation.auditReport.auditSection.error");

		}

		// CUMPLE CONSTRAINT: "startMoment/endMoment must be a valid time interval in future wrt. the moment when an audit report is	published."
		Date now = MomentHelper.getBaseMoment();
		Date start = auditReport.getStartMoment();
		Date end = auditReport.getEndMoment();

		boolean validDates = start != null && end != null && !start.before(now) && end.after(start);

		super.state(context, validDates, "startMoment", "acme.validation.auditReport.dates.error");

		return !super.hasErrors(context);
	}
}
