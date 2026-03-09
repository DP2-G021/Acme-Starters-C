
package acme.features.authenticated.part;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.entities.parts.Part;

@Controller
public class AuthenticatedPartController extends AbstractController<Authenticated, Part> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuthenticatedPartListService.class);
		super.addBasicCommand("show", AuthenticatedPartShowService.class);
	}

}
