
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
import acme.constraints.ValidTicker;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Strategy extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker // modificar valid ticker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	//@ValidHeader //mod 
	@Column
	private String				name;

	@Mandatory
	//@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment() //future?
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
	@Valid
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Mandatory
	@Valid
	// Computed attribute (S1: not implemented yet)
	private Double				monthsActive;

	@Transient
	@Mandatory
	//@ValidScore
	// Computed attribute (S1: not implemented yet)
	private Double				expectedPercentage;

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser			fundraiser;

	// Additional constraints (not implementable in S1):
	// - Strategies cannot be published unless they have at least one tactic.
	// - startMoment/endMoment must be a valid time interval in future wrt. the moment when a strategy is published.
	// - monthsActive is computed as the number of months in interval startMoment/endMoment rounded to the nearest decimal.
	// - The expected percentage is the sum of the expected percentage of each tactic.

}
