
package acme.entities.auditReports;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	// CUMPLE CONSTRAINT (Soporte BD): "Audit reports cannot be published unless they have at least one audit section."
	// Permite comprobar si existen secciones asociadas a este reporte en la base de datos.
	@Query("select count(s) from AuditSection s where s.auditReport.id = :reportId")
	int countByAuditReportId(@Param("reportId") int reportId);

	// CUMPLE CONSTRAINT (Soporte BD): "The number of hours of an audit report is the sum of the individual number of hours in its audit sections."
	// Calcula la suma de las horas directamente mediante una consulta SQL agregada.
	@Query("select sum(s.hours) from AuditSection s where s.auditReport.id = :reportId")
	Integer sumHoursByAuditReportId(@Param("reportId") int reportId);

}
