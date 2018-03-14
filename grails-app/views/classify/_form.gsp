<%@ page import="park.Classify" %>
<%
    def uploader = grailsApplication.config.uploadr.defaultUploadPath
    String classifyPath;
    if(classifyInstance != null && classifyInstance.classifyPath != null){
        classifyPath = classifyInstance.classifyPath;
    }else{
        classifyPath = UUID.randomUUID().getLeastSignificantBits().longValue();
    }
    def path = new File("${uploader}/classify/"+ classifyPath)
    System.out.println(path.getCanonicalPath())
%>
<table>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: classifyInstance, field: 'classifyName', 'error')} required">分类名称</td>
        <td><g:textField name="classifyName" style="width:480px" required="" value="${classifyInstance?.classifyName}"/></td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: classifyInstance, field: 'classifyUrl', 'error')} required">分类图片</td>
        <g:hiddenField name="classifyPath" style="width:480px" required="" value="${classifyPath}" />
        <td>
            <uploadr:add name="classifyUploadr" path="${path}" allowedExtensions="jpg,png,gif" direction="up" maxVisible="1"
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

