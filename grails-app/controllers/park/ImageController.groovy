package park

import grails.converters.JSON

class ImageController {

    def picture(Long pictureId){
        def menuUrls = MenuUrls.findAllByPirtureId(pictureId)
        if(menuUrls && menuUrls.size() > 0){
            def menuUrl = menuUrls.get(0)
            def uploader = grailsApplication.config.uploadr.defaultUploadPath
            def path = new File("${uploader}/imageUploadr/"+ menuUrl.menuUrl)
            File picFile = new File(path.getCanonicalPath() + File.separator + "${menuUrl.fileName}")
            response.contentType = 'image/jpeg'
            response.outputStream << new FileInputStream(picFile)
            response.outputStream.flush()
            response.outputStream.close();
        }
    }

    def thumbnail(Long pictureId){
        def roomUrls = RoomUrls.findAllByPirtureId(pictureId)
        if(roomUrls && roomUrls.size() > 0){
            def roomUrl = roomUrls.get(0)
            def uploader = grailsApplication.config.uploadr.defaultUploadPath
            def path = new File("${uploader}/rooms/"+ roomUrl.roomUrl)
            File picFile = new File(path.getCanonicalPath() + File.separator + "${roomUrl.fileName}")
            response.contentType = 'image/jpeg'
            response.outputStream << new FileInputStream(picFile)
            response.outputStream.flush()
            response.outputStream.close();
        }
    }

    def classify(Long pictureId){
        def classifyUrls = Classify.findAllByPictureId(pictureId)
        if(classifyUrls && classifyUrls.size() > 0){
            def classify= classifyUrls.get(0)
            def uploader = grailsApplication.config.uploadr.defaultUploadPath
            def path = new File("${uploader}/classify/"+ classify.classifyPath)
            File picFile = new File(path.getCanonicalPath() + File.separator + "${classify.fileName}")
            response.contentType = 'image/jpeg'
            response.outputStream << new FileInputStream(picFile)
            response.outputStream.flush()
            response.outputStream.close();
        }
    }
}
