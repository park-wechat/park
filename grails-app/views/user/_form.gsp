<%@ page import="park.User" %>

<table>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">用户名</td>
        <td><g:textField name="username" required="" value="${userInstance?.username}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: userInstance, field: 'chinaName', 'error')}">中文名</td>
        <td><g:textField name="chinaName" value="${userInstance?.chinaName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">密码</td>
        <td><input type="password" name="password" value="${userInstance?.password}">
            %{--<g:textField name="password" required="" value="${userInstance?.password}"/></td>--}%
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">是否启用</td>
        <td><g:checkBox name="enabled" value="${userInstance?.enabled}" /></td>
    </tr>
</table>