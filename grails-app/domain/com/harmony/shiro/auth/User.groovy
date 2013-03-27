package com.harmony.shiro.auth

class User {
    String username
    String passwordHash
    String firstName
    String lastName
    Boolean enabled = true
    
    static hasMany = [ roles: Role, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true, email: true)
        lastName(nullable: true)
        firstName(nullable: true)
    }
}
