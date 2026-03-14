
package acme.features.authenticated.auditor.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreports.AuditReport;
import acme.entities.auditreports.AuditSection;

@Repository
public interface AuditorAuditSectionRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.id = :auditReportId and a.auditor.userAccount.id = :userAccountId")
	AuditReport findAuditReportByIdAndAuditorUserAccountId(int auditReportId, int userAccountId);

	@Query("select a from AuditSection a where a.auditReport.id = :auditReportId and a.auditReport.auditor.userAccount.id = :userAccountId")
	Collection<AuditSection> findAuditSectionsByAuditReportIdAndAuditorUserAccountId(int auditReportId, int userAccountId);

	@Query("select a from AuditSection a where a.id = :id and a.auditReport.auditor.userAccount.id = :userAccountId")
	AuditSection findAuditSectionByIdAndAuditorUserAccountId(int id, int userAccountId);

}
