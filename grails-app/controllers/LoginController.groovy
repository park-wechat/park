import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.util.StringUtils

import javax.servlet.http.HttpServletResponse

class LoginController {

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index = {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    /**
     * Show the login page.
     */
    def auth = {
        def config = SpringSecurityUtils.securityConfig

        def targetURI = request.forwardURI - request.contextPath
        if(!params.lastVisited) {
            session.putAt("lastVisited", "${targetURI+params?.toQueryString()}");
        }else{
            session.putAt("lastVisited", "${params.lastVisited}");
        }

        if (springSecurityService.isLoggedIn()) {
            redirect uri: config.successHandler.defaultTargetUrl
            return
        }

        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                                   rememberMeParameter: config.rememberMe.parameter]
}

    def authSuccess = {
        if(params.uid) {
            def targetUrl = request.getParameter(AbstractAuthenticationTargetUrlRequestHandler.DEFAULT_TARGET_PARAMETER);
            if (StringUtils.hasText(targetUrl)) {
                try {
                    targetUrl = URLDecoder.decode(targetUrl, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible");
                }

                log.debug "Redirecting to target : $targetUrl";
                (new DefaultAjaxAwareRedirectStrategy()).sendRedirect(request, response, targetUrl);
                return;
            }

            def defaultSavedRequest = request.getSession()?.getAttribute(WebAttributes.SAVED_REQUEST)
            log.debug "Redirecting to DefaultSavedRequest : $defaultSavedRequest";
            if(defaultSavedRequest) {
                (new DefaultAjaxAwareRedirectStrategy()).sendRedirect(request, response, defaultSavedRequest.getRedirectUrl());
                return
            } else {
                redirect uri:"/";
                return;
            }
        }
    }

    /**
     * The redirect action for Ajax requests.
     */
    def authAjax = {
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * Show denied page.
     */
    def denied = {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full = {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail = {
        def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "帐号已过期！")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "密码错误！")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "已经被禁用！")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "已经被锁定！")
            }
            else {
                msg = g.message(code: "登录失败！")
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
//			redirect action: 'auth', params: params
            def lastVisited=session.getAt("lastVisited");
            if(lastVisited) {
                redirect uri: lastVisited
            }else{
                redirect action: 'auth', params: params
            }
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess = {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied = {
        render([error: 'access denied'] as JSON)
    }
}

