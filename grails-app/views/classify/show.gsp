<%@ page import="park.Park" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'newsNotice.label', default: 'Park')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-newsNotice" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-newsNotice" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list newsNotice">
			
				<g:if test="${parkInstance?.parkName}">
				<li class="fieldcontain">
					<span id="titleUrl-label" class="property-label"><g:message code="newsNotice.titleUrl.label" default="Title Url" /></span>
					
					<span class="property-value" aria-labelledby="titleUrl-label"><g:fieldValue bean="${parkInstance}" field="parkName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parkInstance?.parkContent}">
				<li class="fieldcontain">
					<span id="content-label" class="property-label"><g:message code="newsNotice.content.label" default="Content" /></span>
					
						<span class="property-value" aria-labelledby="content-label"><g:fieldValue bean="${parkInstance}" field="parkContent"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parkInstance?.crateTime}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="newsNotice.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${parkInstance?.crateTime}" /></span>
					
				</li>
				</g:if>
			</ol>
			<g:form url="[resource:parkInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${parkInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
