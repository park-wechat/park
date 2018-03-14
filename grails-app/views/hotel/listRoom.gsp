<%@ page import="park.Menu" %>
<%@ page import="park.Restaurant" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'console_style.css')}" />
    <g:set var="entityName" value="${message(code: 'newsNotice.label', default: 'NewsNotice')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="content">
    <div class="contiter">
        <h2>房间列表</h2>
    </div>
    <div class="conmain">
        <g:link class="botton" id="${hotel}" action="createRoom">新增房间</g:link>
        %{--<a class="botton" action="createMenu">新增菜谱</a>--}%
    </div>
    <div class="conmain">
        <strong class="Orange">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
        </strong>
    </div>
    <div class="conmain">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tbbg">
                <td width="25%" align="center"><strong>房间名称</strong></td>
                <td width="25%" align="center"><strong>价格</strong></td>
                <td width="25%" align="center"><strong>房间图片</strong></td>
            </tr>
            <g:each in="${roomInstanceList}" status="i" var="roomInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td><g:link action="editRoom" id="${roomInstance.id}">${fieldValue(bean: roomInstance, field: "roomName")}</g:link></td>
                    <td>${fieldValue(bean: roomInstance, field: "price")}</td>
                    <td><img src="${roomInstance.roomPicture}"/></td>
                </tr>
            </g:each>
        </table>
        <div class="pagination">
            <g:paginate total="${roomInstanceCount ?: 0}" />
        </div>
    </div>
</div>
</body>
</html>