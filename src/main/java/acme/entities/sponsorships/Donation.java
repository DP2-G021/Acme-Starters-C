
package acme.entities.sponsorships;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import acme.constraints.ValidText;
import acme.constraints.ValidHeader;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Donation extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				notes;

	@Mandatory
	@ValidMoney(min = 0.0)
	@Column
	private Money				money;

	@Mandatory
	@Enumerated(EnumType.STRING)
	@Column
	private DonationKind		kind;

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne
	private Sponsorship			sponsorship;

}
