<%@ page import="park.Park" %>
<!DOCTYPE html>
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
		<g:set var="entityName" value="${message(code: 'newsNotice.label', default: 'NewsNotice')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
        <ckeditor:resources/>
	</head>
	<body>
    <div class="title">景区修改</div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
			<g:form url="[resource:parkInstance, action:'update']" method="POST" >
				<g:hiddenField name="version" value="${parkInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
                    <g:actionSubmit class="save" action="update" value="更新" />
                    <g:actionSubmit class="delete" action="delete" value="删除" formnovalidate="" onclick="return confirm('确定删除？');" />
				</fieldset>
			</g:form>
	</body>
</html>
