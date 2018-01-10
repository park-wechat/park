

import org.codehaus.groovy.grails.web.json.JSONObject
import org.grails.jaxrs.provider.DomainObjectNotFoundException
import park.User

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserResourceService {
    def springSecurityService

    def create(User dto) {
        dto.save()
    }

    def read(id) {
        def obj = User.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(User.class, id)
        }

        obj
    }

    def readAll() {
        User.findAll()
    }

    def update(User dto) {
        def obj = User.findByUsername(dto.username)
        if (!obj) {
            throw new DomainObjectNotFoundException(User.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    def editPassword(String password, User dto){
        User user=User.findByUsernameAndPassword(dto.username,dto.password)
        if(!user){
            throw new DomainObjectNotFoundException(User.class, dto.id)
        }
        user.password=password
        user.save(failOnError: true)
        return user

    }

    void delete(id) {
        def obj = User.get(id)
        if (obj) {
            obj.delete()
        }
    }

    void afterLogin(HttpServletRequest request, HttpServletResponse response){
        String appID=request.getParameter("appID")
        String username=request.getParameter("username")
        String deviceType=request.getParameter("deviceType")
        String OSVersion=request.getParameter("OSVersion")
        User user=springSecurityService.getCurrentUser()
        //判断是否具有该应用权限
        if(!applicationUser){
            response.sendError(401)
            return
        }

        //判断是否为本人操作
        if(username!=user.username){
            response.sendError(403)
            return
        }

        //判断该用户是否激活
        if(applicationUser.status!=1){
            //进行激活操作
            applicationUser.status=1
            applicationUser.registeredOn=new Date()
        }

        applicationUser.lastAccessedOn=new Date()
        if(deviceType){
            applicationUser.deviceType=deviceType
        }
        if(OSVersion){
            applicationUser.OSVersion=OSVersion
        }
        applicationUser.save(failOnError: true)
    }

    /**
     * 分页获取用户
     * @param pagesize
     * @param startposition
     * @return
     */
    def readAllForPage(Long pagesize, Long startposition){
        JSONObject json = new JSONObject();
        json.put("page", User.list(max: pagesize, offset: startposition))
        json.put("size", User.list().size())
        return  json;

    }
}
