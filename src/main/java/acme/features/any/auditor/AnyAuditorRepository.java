
package acme.features.any.auditor;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.realms.Auditor;

public interface AnyAuditorRepository extends AbstractRepository {

	@Query("select a.auditor from AuditReport a where a.id = :auditorId and a.draftMode = false")
	Auditor findPublishedAuditorByAuditReportId(int auditorId);

}
