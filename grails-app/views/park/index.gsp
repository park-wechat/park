
<%@ page import="com.zy.zds.auth.NewsNotice" %>
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
            <h2>新闻通告列表</h2>
        </div>
        <div class="conmain">
            <a class="botton" href="create">新增</a>
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
                    <td width="70%" align="center"><strong>标题</strong></td>
                    <td width="30%" align="center"><strong>发布时间</strong></td>
                </tr>
				<g:each in="${newsNoticeInstanceList}" status="i" var="newsNoticeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="edit" id="${newsNoticeInstance.id}">${fieldValue(bean: newsNoticeInstance, field: "title")}</g:link></td>
						<td>${g.formatDate(date:newsNoticeInstance.dateCreated, format: 'yyyy-MM-dd')}</td>
					</tr>
				</g:each>
			</table>
			<div class="pagination">
				<g:paginate total="${newsNoticeInstanceCount ?: 0}" />
			</div>
            </div>
		</div>
	</body>
</html>
