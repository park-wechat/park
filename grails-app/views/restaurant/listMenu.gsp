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
        <h2>菜谱列表</h2>
    </div>
    <div class="conmain">
        <g:link class="botton" id="${restaurant}" action="createMenu">新增菜谱</g:link>
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
                <td width="20%" align="center"><strong>菜谱名称</strong></td>
                <td width="20%" align="center"><strong>菜谱分类</strong></td>
                <td width="20%" align="center"><strong>价格</strong></td>
                <td width="20%" align="center"><strong>是否打折</strong></td>
                <td width="20%" align="center"><strong>菜谱图片</strong></td>
            </tr>
            <g:each in="${menuInstanceList}" status="i" var="menuInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td><g:link action="editMenu" id="${menuInstance.id}">${fieldValue(bean: menuInstance, field: "menuName")}</g:link></td>
                    <td>${fieldValue(bean: menuInstance, field: "classifyName")}</td>
                    <td>${fieldValue(bean: menuInstance, field: "price")}</td>
                    <g:if test="${menuInstance.sale > 0}">
                        <td>${fieldValue(bean: menuInstance, field: "sale")}折</td>
                    </g:if>
                    <td><img src="${menuInstance.menuPicture}"/></td>
                </tr>
            </g:each>
        </table>
        <div class="pagination">
            <g:paginate total="${restaurantInstanceCount ?: 0}" />
        </div>
    </div>
</div>
</body>
</html>