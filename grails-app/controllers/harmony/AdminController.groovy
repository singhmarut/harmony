package harmony

import harmony.auth.SecUser
import com.google.gson.Gson
import com.harmony.shiro.auth.User
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.apache.shiro.authz.annotation.RequiresRoles
import com.harmony.shiro.auth.Role

class AdminController {
    def shiroSecurityService

    @RequiresPermissions("ADD_USER_PERMISSION")
    def createUserAccount(){

        def companyRole = Role.findByName('ROLE_COMPANY') ?:
            new Role(name: 'ROLE_COMPANY').save(flush: true)
        User user = new Gson().fromJson(params['user'],User.class)
        user.enabled = true
        user.lastName = user.firstName
        user.passwordHash = shiroSecurityService.encodePassword("admin")
        def createdUser = user.save(flush:  true)

        render new Gson().toJson(createdUser)
    }

    @RequiresPermissions("DELETE_USER_PERMISSION")
    def disableUserAccount(){
        String userName = params['login']
        User userAccount = User.findByUsername(userName)
        if (userAccount){
            userAccount.enabled = false
            userAccount.save()
        }
    }

    def createPermissions(){

    }
    @RequiresRoles("ROLE_ADMIN")
    def list(){
        def userList = User.all
        def userDtoList = new ArrayList<UserDto>()
        for (User user in userList){
            UserDto userDto  = new UserDto()
            userDto.username = user.username
            userDto.enabled = user.enabled
            userDto.firstName = user.firstName
            userDto.lastName = user.lastName
            userDtoList.add(userDto)
        }
        render new Gson().toJson(userDtoList)
    }

    @RequiresRoles("ROLE_ADMIN")
    def index() {
        render(view: "userList")
    }

    class UserDto{
        String username
        String firstName
        String lastName
        boolean enabled
    }
}
