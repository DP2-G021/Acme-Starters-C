
package acme.entities.auditReports;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidTicker;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditReport extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@Column
	private String				name;

	@Mandatory
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Column
	private boolean				draftMode;

	@Mandatory
	@Valid
	@ManyToOne
	private Auditor				auditor;

	@ManyToOne
	private List<AuditSection>	auditSections;


	// CUMPLE CONSTRAINT: "monthsActive is computed as the number of months in interval startMoment/endMoment rounded to the nearest decimal."
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment != null && this.endMoment != null) {
			long diffInMillies = this.endMoment.getTime() - this.startMoment.getTime();
			double months = diffInMillies / (1000.0 * 60 * 60 * 24 * 30.44);
			return Math.round(months * 10.0) / 10.0;
		}
		return 0.0;
	}

	// CUMPLE CONSTRAINT: "The number of hours of an audit report is the sum of the individual number of hours in its audit sections."
	@Transient
	public Integer getHours() {
		int total = 0;
		if (this.auditSections != null)
			for (AuditSection section : this.auditSections)
				if (section.getHours() != null)
					total += section.getHours();
		return total;
	}

	// CUMPLE CONSTRAINT: "Audit reports cannot be published unless they have at least one audit section."
	@AssertTrue(message = "Cannot publish an audit report without at least one audit section")
	public boolean isPublishedWithSections() {
		if (this.draftMode)
			return true;
		return this.auditSections != null && !this.auditSections.isEmpty();
	}

	// CUMPLE CONSTRAINT: "startMoment/endMoment must be a valid time interval in future wrt. the moment when an audit report is published."
	@AssertTrue(message = "Dates must be a valid interval in the future when published")
	public boolean isValidPublicationDates() {
		if (this.draftMode)
			return true;

		if (this.startMoment == null || this.endMoment == null)
			return false;

		Date now = new Date(System.currentTimeMillis() - 2000);
		boolean isStartInFuture = this.startMoment.after(now);
		boolean isIntervalValid = this.endMoment.after(this.startMoment);

		return isStartInFuture && isIntervalValid;
	}

}
