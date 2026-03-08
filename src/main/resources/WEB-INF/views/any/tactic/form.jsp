<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox code="any.tactic.form.label.name" path="name" readonly="true"/>
    <acme:form-textarea code="any.tactic.form.label.notes" path="notes" readonly="true"/>
    <acme:form-double code="any.tactic.form.label.expected-percentage" path="expectedPercentage" readonly="true"/>
    <acme:form-textbox code="any.tactic.form.label.kind" path="kind" readonly="true"/>
    
    <acme:form-return code="any.tactic.form.button.return"/>
</acme:form>
