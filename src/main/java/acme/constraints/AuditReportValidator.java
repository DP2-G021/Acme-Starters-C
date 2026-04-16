
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

		boolean hasAtLeastOneAuditSection;
		if (auditReport.getDraftMode() == null || auditReport.getDraftMode())
			// Si es borrador, no es obligatorio tener hitos aún
			hasAtLeastOneAuditSection = true;
		else {
			// Si NO es borrador (se publica), hay que contar en la base de datos
			Long milestonesCount = this.repository.countByAuditReportId(auditReport.getId());

			if (milestonesCount != null && milestonesCount >= 1L)
				hasAtLeastOneAuditSection = true;
			else
				hasAtLeastOneAuditSection = false;
		}

		super.state(context, hasAtLeastOneAuditSection, "*", "acme.validation.auditReport.auditSection.error");

		// CUMPLE CONSTRAINT: "startMoment/endMoment must be a valid time interval in future wrt. the moment when an audit report is	published."
		boolean validInterval = true;
		boolean validStart = true;
		Date start = auditReport.getStartMoment();
		Date end = auditReport.getEndMoment();

		if (start != null && end != null) {

			boolean positiveInterval = MomentHelper.isBefore(start, end);
			boolean futureStart = MomentHelper.isAfter(start, MomentHelper.getCurrentMoment());

			validInterval = positiveInterval;
			validStart = futureStart;
		}

		super.state(context, validInterval, "endMoment", "acme.validation.invention.invalid-interval.message");
		super.state(context, validStart, "startMoment", "acme.validation.invention.invalid-start.message");

		return !super.hasErrors(context);
	}
}
