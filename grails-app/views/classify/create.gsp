<!DOCTYPE html>
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
		<g:set var="entityName" value="${message(code: 'park.label', default: 'park')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<asset:javascript src="uploadr.manifest.js"/>
		<asset:javascript src="uploadr.demo.manifest.js"/>
		<asset:stylesheet href="uploadr.manifest.css"/>
		<asset:stylesheet href="uploadr.demo.manifest.css"/>
		<r:layoutResources/>
	</head>
	<body>
		<div class="title">分类创建</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			<g:form url="[resource:classifyInstance, action:'save']" >
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
