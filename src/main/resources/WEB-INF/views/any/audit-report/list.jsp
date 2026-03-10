<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.audit-report.list.label.ticker" path="ticker" width="30%"/>
	<acme:list-column code="any.audit-report.list.label.name" path="name" width="30%"/>
	<acme:list-column code="any.audit-report.list.label.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="any.audit-report.list.label.endMoment" path="endMoment" width="15%"/>
</acme:list>