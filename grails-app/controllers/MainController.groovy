import grails.converters.XML
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.servlet.ServletFileUpload
import park.User

import java.text.SimpleDateFormat

class MainController {

    def springSecurityService

    def userResourceService

    def test(){}
    def testData(){
        println(params.aa)
        def uploadedFile = params.bb
        println(uploadedFile.getOriginalFilename())
        def fileName = uploadedFile.getOriginalFilename()
        def filePath = "web-app/"
        uploadedFile.transferTo(new File(filePath+fileName))
    }

    def index() {
        render "success"
    }

    def consoleIndex() {
        User user=springSecurityService.getCurrentUser()
        String dateStr=new SimpleDateFormat("yyyy年MM月dd日").format(new Date())
        [user:user,dateStr:"今天是"+dateStr]
    }
    /**
     * 上传文件
     * @return
     */
    def uploadFile(){
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            String realPath = this.getServletContext().getRealPath("upload");
            File dir = new File(realPath);
            if(!dir.exists()){
                dir.mkdir();
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("utf-8");
            try {
                List<FileItem> items = upload.parseRequest(request);
                for(FileItem item : items){
                    if(item.isFormField()){ //username="username"
                        String name = item.getFieldName();
                        String value = item.getString("utf-8");

                        System.out.println(name + " = " + value);
                    } else { //文件
                        String name = item.getName();
                        item.write(new File(dir, System.currentTimeMillis() + name.substring(name.lastIndexOf("."))));

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    def user(Long id){
        userResourceService.afterLogin(request,response)
        render User.get(id) as XML
    }

    def getXML(){
        response.setHeader("Content-disposition", "attachment; filename=application.xml" )
        response.contentType = "application/xml"

        def out = response.outputStream
        InputStream inputStream = new FileInputStream("C:/Users/ACER/Desktop/周大生/applicationContext.xml")
        byte[] buffer = new byte[1024]
        int i = -1
        while ((i = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, i)
        }
        out.flush()
        out.close()
        inputStream.close()

    }
}
