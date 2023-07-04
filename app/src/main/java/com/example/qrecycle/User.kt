package com.example.qrecycle

import android.media.Image
import android.util.Xml
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat

data class User(
    val id         : String? = null,
    val userName   : String,
    val email      : String,
    val password   : String,
    val points     : Int,
    var isLoggedIn : Boolean = false
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        0,
        true
    )
}
