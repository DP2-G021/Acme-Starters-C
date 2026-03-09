
package acme.features.any.sponsor;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsor;

public class AnySponsorController extends AbstractController<Any, Sponsor> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnySponsorListService.class);
		super.addBasicCommand("show", AnySponsorShowService.class);
	}

}
