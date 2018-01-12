
<%@ page import="com.zy.zds.auth.NewsNotice" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'newsNotice.label', default: 'NewsNotice')}" />
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
			
				<g:if test="${newsNoticeInstance?.titleUrl}">
				<li class="fieldcontain">
					<span id="titleUrl-label" class="property-label"><g:message code="newsNotice.titleUrl.label" default="Title Url" /></span>
					
						<span class="property-value" aria-labelledby="titleUrl-label"><g:fieldValue bean="${newsNoticeInstance}" field="titleUrl"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsNoticeInstance?.writerBy}">
				<li class="fieldcontain">
					<span id="writerBy-label" class="property-label"><g:message code="newsNotice.writerBy.label" default="Writer By" /></span>
					
						<span class="property-value" aria-labelledby="writerBy-label"><g:fieldValue bean="${newsNoticeInstance}" field="writerBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsNoticeInstance?.content}">
				<li class="fieldcontain">
					<span id="content-label" class="property-label"><g:message code="newsNotice.content.label" default="Content" /></span>
					
						<span class="property-value" aria-labelledby="content-label"><g:fieldValue bean="${newsNoticeInstance}" field="content"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsNoticeInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="newsNotice.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${newsNoticeInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsNoticeInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="newsNotice.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${newsNoticeInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsNoticeInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="newsNotice.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${newsNoticeInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:newsNoticeInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${newsNoticeInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
