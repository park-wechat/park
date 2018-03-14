<%@ page import="park.Restaurant" %>
<table>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: restaurant, field: 'restaurantName', 'error')} required">餐厅名称</td>
        <td><g:textField name="restaurantName" style="width:480px" required="" value="${restaurant?.restaurantName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: restaurant, field: 'restaurantOwner', 'error')} required">经营人</td>
        <td><g:textField name="restaurantOwner" style="width:480px" required="" value="${restaurant?.restaurantOwner}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: restaurant, field: 'telephone', 'error')} required">经营人手机号码</td>
        <td><g:textField name="telephone" style="width:480px" required="" value="${restaurant?.telephone}"/></td>
    </tr>
    %{--<tr>--}%
        %{--<td class="fieldcontain ${hasErrors(bean: restaurant, field: 'createDate', 'error')} required">开业时间</td>--}%
        %{--<td><jqueryPicker:date name="createDate" value="${g.formatDate(date:restaurant?.createDate, format: 'yyyy-MM-dd')}"/></td>--}%

    %{--</tr>--}%
</table>


