package park

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class ParkController {

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort= 'id'
        params.order='desc'
        respond Park.list(params), model: [parkInstanceCount: Park.count()]
    }

    def create() {
        respond new Park(params)
    }

    def show(Park parkInstance) {
        respond parkInstance
    }

    @Transactional
    def save(Park parkInstance) {
        if (parkInstance == null) {
            notFound()
            return
        }

        if (parkInstance.hasErrors()) {
            respond parkInstance.errors, view: 'create'
            return
        }
        parkInstance.parkContent="<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0," +
                "maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=device-dpi\">"+parkInstance.parkContent
        parkInstance.crateTime = new Date();
        parkInstance.save flush: true


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'newsNotice.label', default: ''), parkInstance.parkName])
                redirect action: "index", method: "GET"
            }
            '*' { respond parkInstance, [status: CREATED] }
        }
    }

    @Transactional
    def update(Park parkInstance) {
        if (parkInstance == null) {
            notFound()
            return
        }

        if (parkInstance.hasErrors()) {
            respond parkInstance.errors, view: 'edit'
            return
        }
        parkInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'NewsNotice.label', default: ''), parkInstance.parkName])
                redirect action: "index", method: "GET"
            }
            '*' { respond parkInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Park parkInstance) {

        if (parkInstance == null) {
            notFound()
            return
        }

        parkInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NewsNotice.label', default: ''), parkInstance.parkName])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    def edit(Park parkInstance) {
        [parkInstance:parkInstance]
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'newsNotice.label', default: 'Park'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
