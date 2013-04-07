import harmony.auth.SecRole
import harmony.auth.SecUserSecRole
import harmony.auth.SecUser
import harmony.Company
import org.apache.shiro.crypto.hash.Sha256Hash
import com.harmony.shiro.auth.User
import com.harmony.shiro.auth.Role

class BootStrap {

    //def springSecurityService

    def shiroSecurityService
    def init = { servletContext ->

        def adminRole = Role.findByName('ROLE_ADMIN') ?:
            new Role(name: 'ROLE_ADMIN').save(flush: true, failOnError: true)

        def guestRole = Role.findByName('ROLE_CANDIDATE') ?:
            new Role(name: 'ROLE_CANDIDATE').save(flush: true, failOnError: true)

        def user = User.findByUsername("singh.marut@gmail.com")
        if (!user){
            user = new User(username: "singh.marut@gmail.com", passwordHash: shiroSecurityService.encodePassword("admin"))
        }
        user.addToPermissions("*:*")
        user.addToRoles(adminRole)
        user.save(flush: true)

        if (!User.findByUsername("guest")) {
            def passwordHash = shiroSecurityService.encodePassword("axj67q23qn!")
            def guestUser = new User(username: "guest", passwordHash: passwordHash)
            guestUser.addToRoles(guestRole)
            guestUser.save(flush: true)
        }
    }
    def destroy = {
    }
}
