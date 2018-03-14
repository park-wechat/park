<%@ page import="park.Park" %>
<table>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: parkInstance, field: 'parkName', 'error')} required">景区名称</td>
        <td><g:textField name="parkName" style="width:480px" required="" value="${parkInstance?.parkName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: parkInstance, field: 'parkContent', 'error')} required">景区介绍</td>
        <td>
            <ckeditor:editor height="400px" width="100%" name="parkContent">${parkInstance?.parkContent}</ckeditor:editor>
            %{--<textarea name="content" rows="18" cols="100">${newsNoticeInstance?.content}</textarea>--}%
        </td>
    </tr>
</table>

