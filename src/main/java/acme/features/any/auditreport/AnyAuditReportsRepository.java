
package acme.features.any.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreports.AuditReport;

@Repository
public interface AnyAuditReportsRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.id = :auditreportId and a.draftMode = false")
	AuditReport findPublishedAuditReportBy(int auditreportId);

	@Query("select a from AuditReport a where a.draftMode = false")
	Collection<AuditReport> findAllPublishedAuditReports();

}
