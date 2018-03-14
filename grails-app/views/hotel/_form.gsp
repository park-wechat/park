<%@ page import="park.Hotel" %>
<table>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: hotel, field: 'hotelName', 'error')} required">酒店名称</td>
        <td><g:textField name="hotelName" style="width:480px" required="" value="${hotel?.hotelName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: hotel, field: 'hotelOwner', 'error')} required">经营人</td>
        <td><g:textField name="hotelOwner" style="width:480px" required="" value="${hotel?.hotelOwner}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: hotel, field: 'telephone', 'error')} required">经营人手机号码</td>
        <td><g:textField name="telephone" style="width:480px" required="" value="${hotel?.telephone}"/></td>
    </tr>
    %{--<tr>--}%
        %{--<td class="fieldcontain ${hasErrors(bean: restaurant, field: 'createDate', 'error')} required">开业时间</td>--}%
        %{--<td><jqueryPicker:date name="createDate" value="${g.formatDate(date:restaurant?.createDate, format: 'yyyy-MM-dd')}"/></td>--}%

    %{--</tr>--}%
</table>


