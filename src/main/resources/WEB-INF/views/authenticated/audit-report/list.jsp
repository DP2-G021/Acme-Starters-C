<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.audit-report.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="authenticated.audit-report.list.label.name" path="name" width="45%"/>
	<acme:list-column code="authenticated.audit-report.list.label.draftMode" path="draftModeDisplay" width="10%"/>
</acme:list>

<acme:button code="authenticated.audit-report.list.button.create" action="/authenticated/audit-report/create"/>