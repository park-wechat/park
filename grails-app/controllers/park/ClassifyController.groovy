package park

import grails.transaction.Transactional
import org.h2.store.fs.FileUtils

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class ClassifyController {

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort= 'id'
        params.order='desc'
        respond Classify.list(params), model: [classifyInstanceCount: Classify.count()]
    }

    def create() {
        respond new Classify(params)
    }

    def show(Classify classifyInstance) {
        respond classifyInstance
    }

    @Transactional
    def save(Classify classifyInstance) {
        if (classifyInstance == null) {
            notFound()
            return
        }

        if (classifyInstance.hasErrors()) {
            respond classifyInstance.errors, view: 'create'
            return
        }

        def uploader = grailsApplication.config.uploadr.defaultUploadPath
        def path = new File("${uploader}/classify/"+ classifyInstance.classifyPath)
        for(File file : path.listFiles()){
            Long pictureId = UUID.randomUUID().leastSignificantBits;
            classifyInstance.pictureId = pictureId;
            def pictureUrl = createLink(controller: 'image',action: 'classify',params: pictureId)
            classifyInstance.classifyUrl = pictureUrl + "?pictureId="+pictureId;
            classifyInstance.fileName = file.name;
        }

        classifyInstance.save flush: true


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'newsNotice.label', default: ''), classifyInstance.classifyName])
                redirect action: "index", method: "GET"
            }
            '*' { respond classifyInstance, [status: CREATED] }
        }
    }

    @Transactional
    def update(Classify classifyInstance) {
        if (classifyInstance == null) {
            notFound()
            return
        }

        if (classifyInstance.hasErrors()) {
            respond classifyInstance.errors, view: 'edit'
            return
        }

        def uploader = grailsApplication.config.uploadr.defaultUploadPath
        def path = new File("${uploader}/classify/"+ classifyInstance.classifyPath)
        for(File file : path.listFiles()){
            if(classifyInstance.fileName.equals(file.name) && path.listFiles().length > 1){
                FileUtils.delete(file.getCanonicalPath())
            }
        }

        for(File file : path.listFiles()){
            Long pictureId = UUID.randomUUID().leastSignificantBits;
            classifyInstance.pictureId = pictureId;
            def pictureUrl = createLink(controller: 'image',action: 'classify',params: pictureId)
            classifyInstance.classifyUrl = pictureUrl + "?pictureId="+pictureId;
            classifyInstance.fileName = file.name;
        }
        classifyInstance.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'NewsNotice.label', default: ''), classifyInstance.classifyName])
                redirect action: "index", method: "GET"
            }
            '*' { respond classifyInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Classify classifyInstance) {

        if (classifyInstance == null) {
            notFound()
            return
        }

        def uploader = grailsApplication.config.uploadr.defaultUploadPath
        def path = new File("${uploader}/classify/"+ classifyInstance.classifyPath);
        if(path.exists()){
            if(!path.deleteDir()){
                System.out.println("can not delete file, path=["+path+"]")
            }
        }

        classifyInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NewsNotice.label', default: ''), classifyInstance.classifyName])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    def edit(Classify classifyInstance) {
        [classifyInstance:classifyInstance]
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'newsNotice.label', default: ''), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
