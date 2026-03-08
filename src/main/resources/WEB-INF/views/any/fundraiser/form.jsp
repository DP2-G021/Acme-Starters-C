<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox code="any.fundraiser.form.label.identity" path="identity" readonly="true"/>
    <acme:form-textbox code="any.fundraiser.form.label.bank" path="bank" readonly="true"/>
    <acme:form-textarea code="any.fundraiser.form.label.statement" path="statement" readonly="true"/>
    <acme:form-checkbox code="any.fundraiser.form.label.agent" path="agent" readonly="true"/>
    
    <acme:form-return code="any.fundraiser.form.button.return"/>
</acme:form>
