
package acme.features.authenticated.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;

@Controller
public class SponsorSponsorshipController extends AbstractController<Authenticated, Sponsorship> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", SponsorSponsorshipListService.class);
		super.addBasicCommand("show", SponsorSponsorshipShowService.class);
	}

}
