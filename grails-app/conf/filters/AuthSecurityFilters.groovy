package filters

import park.User;

class AuthSecurityFilters {

    def springSecurityService

    def filters = {
        loginCheck(controller:'*') {
            before = {
                if (controllerName == 'main') {
                    User user = springSecurityService.getCurrentUser()
                    if (!user) {
                        redirect(controller: 'login', action: 'index')
                        return false
                    } else if (!user.isAdmin()) {
                        redirect(controller: 'logout', action: 'index')
                        return false
                    }
                }
                return true;
            }
        }
    }
}
