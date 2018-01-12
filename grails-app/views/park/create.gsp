<!DOCTYPE html>
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
		<g:set var="entityName" value="${message(code: 'newsNotice.label', default: 'NewsNotice')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <ckeditor:resources/>
	</head>
	<body>
    <div class="title">新闻培训新增界面</div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
			<g:form url="[resource:newsNoticeInstance, action:'save']" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="创建" />
                    <g:link action="index" class="button">返回</g:link>
				</fieldset>
			</g:form>
	</body>
</html>
