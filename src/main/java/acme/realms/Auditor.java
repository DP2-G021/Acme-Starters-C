
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidString
	@Column
	private String				firm;

	@Mandatory
	@ValidString
	@Column
	private String				highlights;

	@Mandatory
	@Column
	private Boolean				solicitor;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
