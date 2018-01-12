<%@ page import="com.zy.zds.auth.User" %>
<!DOCTYPE html>
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
    <div class="title">用户更新界面</div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
			<g:form url="[resource:userInstance, action:'update']" method="PUT" >
				<g:hiddenField name="version" value="${userInstance?.version}" />
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
