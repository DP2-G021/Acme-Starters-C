
package acme.features.authenticated.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreports.AuditReport;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("SELECT a FROM AuditReport a WHERE a.auditor.userAccount.id = :userAccountId")
	Collection<AuditReport> findAuditReportsByAuditorUserAccountId(int userAccountId);

	@Query("SELECT a FROM AuditReport a WHERE a.id = :id AND a.auditor.userAccount.id = :userAccountId")
	AuditReport findAuditReportByIdAndAuditorUserAccountId(int id, int userAccountId);

}
