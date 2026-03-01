
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidSponsorship;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidSponsorship

public class Sponsorship extends AbstractEntity {

	@Transient
	@Autowired
	private SponsorshipRepository	repository;

	// Serialisation version --------------------------------------------------
	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String					name;

	@Mandatory
	@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;

	// Derived attributes -----------------------------------------------------

	//@Mandatory


	//CONSTRAINT:monthsActive is computed as the number of months in interval startMoment/endMoment rounded to the nearest decimal.
	@Valid
	@Transient
	private Double getMonthsActive() { // cambiar
		if (this.startMoment != null && this.endMoment != null) {
			long diffInMillies = this.endMoment.getTime() - this.startMoment.getTime();
			double months = diffInMillies / (1000.0 * 60 * 60 * 24 * 30);
			return Math.round(months * 10.0) / 10.0;
		}
		return 0.0;
	}

	//CONSTRAINT: The total money of a sponsorship is the sum of money in the corresponding donations. Only Euros are accepted.
	//@Mandatory
	//@ValidMoney(min = 0.0)

	@Transient
	private Money getTotalMoney() {
		Double sum = this.repository.getSumTotalMoneyBySponsorship(this.getId());
		double total = sum != null ? sum : 0.0;
		Money result = new Money();
		result.setAmount(sum);
		result.setCurrency("EUR");
		return result;
	}


	// Relationships ----------------------------------------------------------
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;

	// Sponsorships cannot be published unless they have at least one donation.

	//startMoment/endMoment must be a valid time interval in future wrt. the moment when a sponsorship is published.

	//monthsActive is computed as the number of months in interval startMoment/endMoment rounded to the nearest decimal.

	//The total money of a sponsorship is the sum of money in the corresponding donations. Only Euros are accepted.
}
