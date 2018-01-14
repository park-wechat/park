<%@ page import="park.Room" %>
<%@ page import="park.Hotel" %>
<%
    def uploader = grailsApplication.config.uploadr.defaultUploadPath
    String roomUrl = (room != null ? room.roomUrl : (UUID.randomUUID().getLeastSignificantBits().longValue()))
    def path = new File("${uploader}/rooms/"+ roomUrl)
%>
<table>
    %{--<tr>--}%
        %{--<td class="fieldcontain ${hasErrors(bean:menu, field: 'restaurant', 'error')} required">餐厅名称</td>--}%
        %{--<td><g:hiddenField name="restaurant_id" style="width:480px" required="" value="${restaurant}"/></td>--}%
    %{--</tr>--}%
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'roomName', 'error')} required">房间名称</td>
        <td><g:textField name="roomName" style="width:480px" required="" value="${room?.roomName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'price', 'error')} required">价格</td>
        <td><g:textField name="price" style="width:480px" required="" value="${room?.price}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'roomUrl', 'error')} required">房间图片</td>
        <g:hiddenField name="roomUrl" style="width:480px" required="" value="${roomUrl}" />
        <td>
            <uploadr:add name="editUploadr" path="${path}" allowedExtensions="jpg,png,gif" direction="up" maxVisible="10"
                         unsupported="${createLink(plugin: 'uploadr', controller: 'upload', action: 'warning')}" maxSize="52428800"
                         model="[booleanOne:true, variableTwo: 'foo', variableThree: 'bar', variableFour: 4, myObject: someObject]">
                <g:each in="${path.listFiles()}" var="file">
                    <uploadr:file name="${file.name}">
                        <uploadr:fileSize>${file.size()}</uploadr:fileSize>
                        <uploadr:fileModified>${file.lastModified()}</uploadr:fileModified>
                        %{--<uploadr:fileId>myId-${RandomStringUtils.random(32, true, true)}</uploadr:fileId>--}%
                    </uploadr:file>
                </g:each>
            </uploadr:add>
        </td>
    </tr>
</table>