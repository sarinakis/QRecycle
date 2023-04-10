package com.example.qrecycle

data class User(
    val id : String? = null,
    val name : String,
    val email : String,
    val password : String
) {
    constructor() : this("", "", "", "")
}
