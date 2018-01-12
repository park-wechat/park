package com.zy.zds.auth

import com.google.gson.Gson
import com.zy.zds.vo.Store
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class UserController {

//    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort= 'id'
        params.order='desc'
        respond User.list(params), model:[userInstanceCount: User.count()]
    }

    def getUser(){
        def userInstanceList=User.findAllByUsernameLike("%"+params.searchString+"%")
        [userInstanceList:userInstanceList]
    }

    def getUserByStore(){
        Store store=Store.get(params.store)
        def userInstanceList=User.findAllByStore(store.zzmdmc)
        String s=""
        userInstanceList.each {
            s=s+it.username+","
        }
        render s
//        [userInstanceList:userInstanceList]
    }

    def show(User userInstance) {
        respond userInstance
    }

    def create() {
        respond new User(params)
    }

    @Transactional
    def save(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'create'
            return
        }

        userInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { respond userInstance, [status: CREATED] }
        }
    }

    def edit(User userInstance) {
        respond userInstance
    }

    @Transactional
    def update(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'edit'
            return
        }

        userInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect action: "index", method: "GET"
            }
            '*'{ respond userInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def autoUsers(){
        def stores = Store.findAllByPartnerLike("%"+params.searchItem+"%");
        def result = new ArrayList<Store>();

        if(stores != null){
            if(stores.size() > 10){
                for(int i=0; i<10; i++){
                    result.add(stores.get(i));
                }
            }else{
                for(int i=0; i<stores.size(); i++){
                    result.add(stores.get(i));
                }
            }
        }
        def son = new Gson();

        String json = son.toJson(result);

        println("json="+json);

        response.writer.write(json);
    }

}
