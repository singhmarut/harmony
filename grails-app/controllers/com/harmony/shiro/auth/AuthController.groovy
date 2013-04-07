package com.harmony.shiro.auth

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils
import org.apache.shiro.grails.ConfigUtils
import org.apache.shiro.subject.Subject
import com.google.gson.Gson
import grails.converters.JSON

class AuthController {
    def shiroSecurityManager
    def testKeyGeneratorService
    def shiroSecurityService
    def harmonyMailService

    def index = { redirect(action: "login", params: params) }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def signIn = {
        def authToken = new UsernamePasswordToken(params.username, params.password as String)

        // Support for "remember me"
        if (params.rememberMe) {
            authToken.rememberMe = true
        }
        
        // If a controller redirected to this page, redirect back
        // to it. Otherwise redirect to the root URI.
        def targetUri = params.targetUri ?: "/"
        
        // Handle requests saved by Shiro filters.
        def savedRequest = WebUtils.getSavedRequest(request)
        if (savedRequest) {
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        }
        
        try{
            User user = User.findByUsername(params.username)
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            SecurityUtils.subject.login(authToken)
            if (user){
                SecurityUtils.subject.session.setAttribute("userId", user.id)
            }

            log.info "Redirecting to '${targetUri}'."
            redirect(uri: targetUri)
        }
        catch (AuthenticationException ex){
            // Authentication failed, so display the appropriate message
            // on the login page.
            log.info "Authentication failure for user '${params.username}'."
            flash.message = message(code: "login.failed")

            // Keep the username and "remember me" setting so that the
            // user doesn't have to enter them again.
            def m = [ username: params.username ]
            if (params.rememberMe) {
                m["rememberMe"] = true
            }

            // Remember the target URI too.
            if (params.targetUri) {
                m["targetUri"] = params.targetUri
            }

            // Now redirect back to the login page.
            redirect(action: "login", params: m)
        }
    }

    def signOut = {
        // Log the user out of the application.
        def principal = SecurityUtils.subject?.principal
        SecurityUtils.subject?.logout()
        // For now, redirect back to the home page.
        if (ConfigUtils.getCasEnable() && ConfigUtils.isFromCas(principal)) {
            redirect(uri:ConfigUtils.getLogoutUrl())
        }else {
            redirect(uri: "/")
        }
        ConfigUtils.removePrincipal(principal)
    }

    def unauthorized = {
        render "You do not have permission to access this page."
    }

    def authorizeKey= {
        String authKey = params.authKey
        if (!testKeyGeneratorService.findQuestionPaper(authKey) ){
            flash.message = "Wrong Authroization Key. Please try again"
            render view: "login"
        }else{
            User user = User.findByUsername(params.email)
            if (!user){
                user = createNewCandidate(params.email)
            }
            /**
             * At the time of test load we will login user as guest only
             */
            UsernamePasswordToken token = new UsernamePasswordToken("guest", "admin");
            Subject currentUser = SecurityUtils.getSubject();
            SecurityUtils.subject.login(token)
            params.username = "guest"
            params.password = "admin"
            currentUser.session.setAttribute("authKey", authKey)
            registerCandidate(user)
            flash.message = ""
            redirect(controller: 'questionPaper', action: 'loadQuestionPaper')
            //redirect(action: "signIn", params: params)
        }
    }

    def createNewCandidate(String email){
        Role role = Role.findByName("ROLE_CANDIDATE")
        //Create a new user and bind this Test Key to this user
        //currentUser.session.setAttribute("authKey", authKey)
        User user  = new User()
        user.username = params.email
        String password = testKeyGeneratorService.createPasswordForCandidate()
        user.passwordHash = shiroSecurityService.encodePassword(password)
        user.addToRoles(role)
        user = user.save(flush: true)
        harmonyMailService.sendCandidateAccountCreationMail(user,password)
        return user
    }

    def registerCandidate(User user){
        Subject currentUser = SecurityUtils.getSubject()
        if (currentUser)
        {
            def authKey = currentUser.session.getAttribute("authKey")
            testKeyGeneratorService.bindAuthKeyWithCandidate(authKey, user.id)

        }
    }
}

class UserDto{
    String email
    String password
}

class HarmonyResponse{
    int status
    String message
}
