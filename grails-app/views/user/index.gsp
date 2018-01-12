
<%@ page import="com.zy.zds.auth.User" %>
<!DOCTYPE html>
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'console_style.css')}" />
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
    <div class="content">
        <div class="contiter">
            <h2>用户列表</h2>
        </div>
        <div class="conmain">
            <g:link class="botton" action="create">新增</g:link>
            <g:form url="[action:'getUser',controller:'user']" >
                <input name="searchString" id="search" />
                <input type="submit" style="float:right;margin-right:640px;" value="查询" />
            </g:form>
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
                    <td width="30%" align="center"><strong>用户名</strong></td>
                    <td width="30%" align="center"><strong>中文名</strong></td>
                    <td width="40%" align="center"><strong>状态</strong></td>
                </tr>
				<g:each in="${userInstanceList}" status="i" var="userInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="edit" id="${userInstance.id}">${fieldValue(bean: userInstance, field: "username")}</g:link></td>

                        <td>${userInstance.chinaName?:"无"}</td>

						<td>${userInstance.enabled?"启用":"停用"}</td>
					
					</tr>
				</g:each>
			</table>
			<div class="pagination">
				<g:paginate total="${userInstanceCount ?: 0}" />
			</div>
		</div>
        </div>
	</body>
</html>
