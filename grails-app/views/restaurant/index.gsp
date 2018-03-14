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
            <h2>餐厅列表</h2>
        </div>
        <div class="conmain">
            <a class="botton" href="create">新增餐厅</a>
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
                    <td width="20%" align="center"><strong>餐厅名称</strong></td>
                    <td width="20%" align="center"><strong>经营人</strong></td>
                    <td width="20%" align="center"><strong>经营人手机号码</strong></td>
                    <td width="20%" align="center"><strong>开业时间</strong></td>
                    <td width="20%" align="center"><strong>管理菜谱</strong></td>
                    <td width="20%" align="center"><strong>操作</strong></td>
                </tr>
				<g:each in="${restaurantInstanceList}" status="i" var="restaurantInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="edit" id="${restaurantInstance.id}">${fieldValue(bean: restaurantInstance, field: "restaurantName")}</g:link></td>
                        <td>${fieldValue(bean: restaurantInstance, field: "restaurantOwner")}</td>
                        <td>${fieldValue(bean: restaurantInstance, field: "telephone")}</td>
                        <td>${g.formatDate(date:restaurantInstance.createDate, format: 'yyyy-MM-dd')}</td>
                        <td><g:link action="listMenu" id="${restaurantInstance.id}" class="delete" >菜单管理</g:link></td>
                        <td>
                            <g:form url="[resource:restaurantInstance, action:'delete']" method="POST" >
                                <g:actionSubmit class="delete" value="删除" action="delete" onclick="return confirm('确定删除？');" />
                            </g:form>
                        </td>
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
