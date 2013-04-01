package com.harmony.shiro.auth

class User {

    String username
    String passwordHash
    String firstName
    String lastName
    Long phoneNumber
    String company = 'ProspectHire'
    Boolean enabled = true
    
    static hasMany = [ roles: Role, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true, email: true)
        lastName(nullable: true)
        firstName(nullable: true)
        phoneNumber(nullable: true)
    }
}
