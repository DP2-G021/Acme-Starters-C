
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidLongText;
import acme.constraints.ValidShortText;
import acme.constraints.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation version --------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidShortText
	@Column
	private String				name;

	@Mandatory
	@ValidLongText
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

	// Derived attributes -----------------------------------------------------

	@Mandatory
	@Valid
	@Transient
	private Double				monthsActive;

	@Mandatory
	@ValidMoney(min = 0.0)
	@Transient
	private Money				totalMoney;

	// Relationships ----------------------------------------------------------
	@Mandatory
	@Valid
	@ManyToOne
	private Sponsor				sponsor;

	//Additional constraints
	//Sponsorships cannot be published unless they have at least one donation.

	//startMoment/endMoment must be a valid time interval in future wrt. the moment when a sponsorship is published.

	//monthsActive is computed as the number of months in interval startMoment/endMoment rounded to the nearest decimal.

	//The total money of a sponsorship is the sum of money in the corresponding donations. Only Euros are accepted.
}
