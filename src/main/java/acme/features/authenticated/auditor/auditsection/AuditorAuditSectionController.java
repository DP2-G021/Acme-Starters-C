
package acme.features.authenticated.auditor.auditsection;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.entities.auditreports.AuditSection;

@Controller
public class AuditorAuditSectionController extends AbstractController<Authenticated, AuditSection> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", AuditorAuditSectionListService.class);
		super.addBasicCommand("show", AuditorAuditSectionShowService.class);
	}

}
