
package acme.features.any.donation;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Donation;

public class AnyDonatioController extends AbstractController<Any, Donation> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnyDonationListService.class);
		super.addBasicCommand("show", AnyDonationShowService.class);
	}

}
