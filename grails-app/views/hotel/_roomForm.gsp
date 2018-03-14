<%@ page import="park.Room" %>
<%@ page import="park.Hotel" %>
<%
    def uploader = grailsApplication.config.uploadr.defaultUploadPath
    String roomUrl = (room != null ? room.roomUrl : (UUID.randomUUID().getLeastSignificantBits().longValue()))
    def path = new File("${uploader}/rooms/"+ roomUrl)
%>
<table>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'roomName', 'error')} required">房间名称</td>
        <td><g:textField name="roomName" style="width:480px" value="${room?.roomName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'price', 'error')} required">价格</td>
        <td><g:textField name="price" style="width:480px" required="" value="${room?.price}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'roomLevel', 'error')} required">房间等级</td>
        <td><g:textField name="roomLevel" style="width:480px" required="" value="${room?.roomLevel}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'roomInformation', 'error')} required">房间介绍</td>
        <td><g:textArea name="roomInformation" style="width:480px" required="" value="${room?.roomInformation}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: room, field: 'mark', 'error')} required">备注/td>
        <td><g:textArea name="mark" style="width:480px" required="" value="${room?.mark}"/></td>
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