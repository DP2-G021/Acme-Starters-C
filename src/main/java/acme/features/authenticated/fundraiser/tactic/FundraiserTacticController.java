
package acme.features.authenticated.fundraiser.tactic;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.entities.strategies.Tactic;

@Controller
public class FundraiserTacticController extends AbstractController<Authenticated, Tactic> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", FundraiserTacticListService.class);
		super.addBasicCommand("show", FundraiserTacticShowService.class);
	}
}
