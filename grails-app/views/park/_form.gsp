<%@ page import="com.zy.zds.auth.NewsNotice" %>
<table>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: newsNoticeInstance, field: 'title', 'error')} required">标题</td>
        <td><g:textField name="title" style="width:480px" required="" value="${newsNoticeInstance?.title}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: newsNoticeInstance, field: 'isSupportAnonymous', 'error')} ">是否可以匿名访问</td>
        <td><g:checkBox name="isSupportAnonymous" value="${newsNoticeInstance?.isSupportAnonymous}" /></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: newsNoticeInstance, field: 'type', 'error')} required">类型</td>
        <td>
            <input type="radio" name="type" value=1 ${newsNoticeInstance?.type==1?'checked':''}>新闻
            <input type="radio" name="type" value=2 ${newsNoticeInstance?.type==2?'checked':''}>培训
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: newsNoticeInstance, field: 'content', 'error')} required">内容</td>
        <td>
            <ckeditor:editor height="400px" width="100%" name="content">${newsNoticeInstance?.content}</ckeditor:editor>
            %{--<textarea name="content" rows="18" cols="100">${newsNoticeInstance?.content}</textarea>--}%
        </td>
    </tr>
    <g:if test="${newsNoticeInstance?.type==2}">
        <tr>
            <td class="fieldcontain required">店铺管理员反馈二维码</td>
            <td><img  src="${managerURL}" /></td>
        </tr>
        <tr>
            <td class="fieldcontain required">店铺学员反馈二维码</td>
            <td><img  src="${studentURL}" /></td>
        </tr>
        <tr>
            <td colspan="2"><g:link action="showResult" id="${newsNoticeInstance?.id}">查看反馈情况</g:link></td>
        </tr>
    </g:if>
</table>

