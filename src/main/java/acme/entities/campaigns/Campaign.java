
package acme.entities.campaigns;

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
import acme.constraints.ValidCampaign;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidCampaign
public class Campaign extends AbstractEntity {

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
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;
		long diffMillis = this.endMoment.getTime() - this.startMoment.getTime();
		double meses = diffMillis / (1000.0 * 60 * 60 * 24 * 30);
		return Math.round(meses * 10.0) / 10.0;
	}

	@Transient
	public Double getEffort() {
		CampaignRepository repo = SpringHelper.getBean(CampaignRepository.class);
		Double total = repo.calculateTotalEffort(this.getId());
		return total != null ? total : 0.0;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne
	private Spokesperson spokesperson;
}
