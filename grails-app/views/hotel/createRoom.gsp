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
    <div class="title">菜谱创建</div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:form url="[resource:menu,  action:'saveMenu']" method="POST" >
        <fieldset class="form">
            <g:render template="menuForm"/>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit id="${restaurant}" name="create" action="saveMenu" class="save" value="创建" />
            <g:link action="listMenu" class="button">返回</g:link>
        </fieldset>
    </g:form>
    <r:layoutResources/>
</body>
</html>