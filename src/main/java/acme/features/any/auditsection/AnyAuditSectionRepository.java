
package acme.features.any.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreports.AuditReport;
import acme.entities.auditreports.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.id = :id and a.draftMode = false")
	AuditReport findPublishedAuditReportById(int id);

	@Query("select a from AuditSection a where a.id = :auditSectiontId and a.auditReport.draftMode = false")
	AuditSection findPublishedAuditSectionById(int auditSectiontId);

	@Query("select a from AuditSection a where a.auditReport.id = :auditReportId and a.auditReport.draftMode = false")
	Collection<AuditSection> findAuditSectionsByPublishedAuditReportId(int auditReportId);
}
