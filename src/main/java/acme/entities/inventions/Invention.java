
package acme.entities.inventions;

import java.time.temporal.ChronoUnit;
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
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidInvention;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.parts.PartRepository;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidInvention
public class Invention extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Transient
	@Autowired
	PartRepository				repo;

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
	//@Valid  //HINT: Eclipse's validator forbids this annotation here.
	@Transient
	public Double getMonthsActive() {

		if (this.startMoment == null || this.endMoment == null)
			return 0.0;

		double months = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);

		return Math.round(months * 10.0) / 10.0;

	}

	@Mandatory
	@ValidMoney(min = 0.0)
	@Transient
	public Money getCost() {
		Money result = new Money();

		Double total = this.repo.getInventionCost(this.getId());

		if (total == null)
			total = 0.0; //ask about this politic

		result.setAmount(total);
		result.setCurrency("EUR");
		return result;

	}


	@Mandatory
	//HINT: @Valid by default
	@Column
	private boolean		draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Inventor	inventor;

}
