
package acme.entities.inventions;

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
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.SpringHelper;
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


	@Valid //ask manuel jesus about the use of the valid in here
	@Transient
	public Double getMonthsActive() {

		if (this.startMoment == null || this.endMoment == null)
			return 0.0; //ask manuel jesus about if this is a great politic

		double seconds = MomentHelper.computeDuration(this.startMoment, this.endMoment).getSeconds(); //esto va a dar una excepción, lo traigo en segudos y hago la conversión
		double months = seconds / (60.0 * 60.0 * 24.0 * 30.0);

		return Math.round(months * 10.0) / 10.0;

	}

	@Valid
	@Transient
	public Money getCost() {
		Money result = new Money();

		PartRepository repo = SpringHelper.getBean(PartRepository.class);
		Double total = repo.getInventionCost(this.getId());

		if (total == null)
			total = 0.0; //ask about this politic

		result.setAmount(total);
		result.setCurrency("EURO");
		return result;

	}


	@Mandatory
	@Valid
	@Column
	private Boolean		draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Inventor	inventor;

}
