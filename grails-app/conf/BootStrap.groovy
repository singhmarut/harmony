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

        def user = new User(username: "singh.marut@gmail.com", passwordHash: shiroSecurityService.encodePassword("admin"))
        user.addToPermissions("*:*")
        user.addToRoles(adminRole)
        user.save(flush: true)
        Company company = new Company()
        company.shortName = user.username
        company.save()

//        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
//        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
//        def findAdminUser = SecUser.findByUsername('admin')
//        def adminUser = findAdminUser ?: new Company(
//                username: 'admin',
//                fullName: "Prospect Hire",
//                shortName: "Prospect Hire",
//                password: 'admin',
//                enabled: true).save()
//        if (!findAdminUser){
//            Company company = new Company()
//            company.fullName = "Prospect Hire"
//            company.shortName = "ProspectHire"
//            company.save()
//            //adminUser.save(failOnError: true)
//        }

//        if (!adminUser.authorities.contains(adminRole)) {
//            SecUserSecRole.create adminUser, adminRole
//        }

    }
    def destroy = {
    }
}
