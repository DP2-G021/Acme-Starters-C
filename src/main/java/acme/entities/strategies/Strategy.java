
package acme.entities.strategies;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.SpringHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@ValidStrategy
@Entity
@Getter
@Setter
public class Strategy extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Column
	@Valid
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	// CUMPLE CONSTRAINT: "monthsActive is computed as the number of months in interval startMoment/endMoment rounded to the nearest decimal."
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment != null && this.endMoment != null) {
			long diffInMillies = this.endMoment.getTime() - this.startMoment.getTime();
			double months = diffInMillies / (1000.0 * 60 * 60 * 24 * 30);
			return Math.round(months * 10.0) / 10.0;
		}
		return 0.0;
	}

	// CUMPLE CONSTRAINT: "The expected percentage is the sum of the expected percentage of each tactic."
	@Transient
	public Double getExpectedPercentage() {
		StrategyRepository repository;
		Double sum;

		repository = SpringHelper.getBean(StrategyRepository.class);
		sum = repository.sumExpectedPercentageByStrategyId(this.getId());
		return sum == null ? 0.0 : sum;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser fundraiser;

}
