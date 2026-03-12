<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.donation.form.label.name" path="name" readonly="true"/>
	<acme:form-money code="authenticated.donation.form.label.money" path="money" readonly="true"/>	
	<acme:form-textbox code="authenticated.donation.form.label.kind" path="kind" readonly="true"/>
	<acme:form-textarea code="authenticated.donation.form.label.notes" path="notes" readonly="true"/>
		
</acme:form>