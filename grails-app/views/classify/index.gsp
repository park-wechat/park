<%@ page import="park.Park" %>
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
            <h2>分类列表</h2>
        </div>
        <div class="conmain">
            <a class="botton" href="create">新增分类</a>
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
                    <td width="50%" align="center"><strong>分类名称</strong></td>
                    <td width="50%" align="center"><strong>分类图片</strong></td>
                </tr>
				<g:each in="${classifyInstanceList}" status="i" var="classifyInstanc">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="edit" id="${classifyInstanc.id}">${fieldValue(bean: classifyInstanc, field: "classifyName")}</g:link></td>
                        <td><img src="${classifyInstanc.classifyUrl}"/></td>
					</tr>
				</g:each>
			</table>
			<div class="pagination">
				<g:paginate total="${classifyInstanceCount ?: 0}" />
			</div>
            </div>
		</div>
	</body>
</html>
