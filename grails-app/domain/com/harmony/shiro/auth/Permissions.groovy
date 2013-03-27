package com.harmony.shiro.auth

class Permissions {

    String permission
    static constraints = {
    }
}
class PermissionList{
    public static final String ADD_USER = "ADD_USER_PERMISSION"
    public static final String DELETE_USER = "DELETE_USER_PERMISSION"
    public static final String UPDATE_USER = "UPDATE_USER_PERMISSION"
    public static final String ADD_QUESTION = "ADD_QUESTION_PERMISSION"
    public static final String DELETE_QUESTION = "DELETE_QUESTION_PERMISSION"
    public static final String UPDATE_QUESTION = "UPDATE_QUESTION_PERMISSION"

}
