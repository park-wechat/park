

/**
 * Created by oswaldl on 7/19/2014.
 */
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent

class MySecurityEventListener
        implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private UserResourceService userResourceService
    void onApplicationEvent(AuthenticationSuccessEvent event) {
//        userResourceService.readAll().each{
//            println "user: "+it
//        }
//        println "hello "+userResourceService
    }
}
