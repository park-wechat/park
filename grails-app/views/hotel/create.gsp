<!DOCTYPE html>
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
		<g:set var="entityName" value="${message(code: 'park.label', default: 'park')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<r:require module="jquery-ui" />
		<r:require module="timepicker"/>

		<r:layoutResources/>
	</head>
	<body>
		<div class="title">酒店创建</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			<g:form url="[resource:hotel, action:'save']" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="创建" />
					<g:link action="index" class="button">返回</g:link>
				</fieldset>
			</g:form>
		<r:layoutResources/>
		</body>
</html>
