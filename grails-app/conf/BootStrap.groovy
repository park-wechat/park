import park.User;
import park.UserRole;
import park.Role;

class BootStrap {

    def init = { servletContext ->
        def users=User.findAllByUsername('admin')
        if(users&&users.size()>0){
            return;
        }

        def user = User.findByUsername('admin') ?: new User(
                username: 'admin',
                chineseName: '管理员',
                password: 'admin',
                enabled: true).save(flush: true)

        def adminRole = Role.findByAuthority('ROLE_ADMIN')
        if (!adminRole) {
            adminRole = new Role(authority: 'ROLE_ADMIN')
            adminRole.save(flush: true)
        }

        if (!user.getAuthorities().contains(adminRole)) {
            UserRole userRole = new UserRole(user, adminRole)
            userRole.save(flush: true)
        }

    }
    def destroy = {
    }
}
