<%@ page import="park.Menu" %>
<%@ page import="park.Restaurant" %>
<%
    def uploader = grailsApplication.config.uploadr.defaultUploadPath
    String menuUrl = (menu != null ? menu.menuUrl : (UUID.randomUUID().getLeastSignificantBits().longValue()))
    System.out.println("menuUrl----->"+menuUrl)
    def path = new File("${uploader}/imageUploadr/"+ menuUrl)
    System.out.println(path.getCanonicalFile())
%>
<table>
    %{--<tr>--}%
        %{--<td class="fieldcontain ${hasErrors(bean:menu, field: 'restaurant', 'error')} required">餐厅名称</td>--}%
        %{--<td><g:hiddenField name="restaurant_id" style="width:480px" required="" value="${restaurant}"/></td>--}%
    %{--</tr>--}%
    <tr>
        <td class="fieldcontain ${hasErrors(bean: menu, field: 'menuName', 'error')} required">菜谱名称</td>
        <td><g:textField name="menuName" style="width:480px" readonly="true" value="${menu?.menuName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: menu, field: 'price', 'error')} required">价格</td>
        <td><g:textField name="price" style="width:480px" required="" value="${menu?.price}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: menu, field: 'sale', 'error')} required">折数</td>
        <td><g:textField name="sale" style="width:480px" required="" value="${menu?.sale}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: menu, field: 'menuIntroduce', 'error')} required">菜谱介绍</td>
        <td><g:textArea name="menuIntroduce" style="width:480px" required="" value="${menu?.menuIntroduce}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: menu, field: 'menuInfomation', 'error')} required">菜谱成分</td>
        <td><g:textArea name="menuInfomation" style="width:480px" required="" value="${menu?.menuInfomation}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: menu, field: 'mark', 'error')} required">备注</td>
        <td><g:textArea name="mark" style="width:480px" required="" value="${menu?.mark}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: menu, field: 'menuUrl', 'error')} required">菜谱图片</td>
        <g:hiddenField name="menuUrl" style="width:480px" required="" value="${menuUrl}" />
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