package com.meetferrytan.kotlinmvptemplate.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by ferrytan on 11/17/17.
 */

class User {
    @SerializedName("login")
    var login: String? = null
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("avatar_url")
    var avatar: String? = null
    @SerializedName("location")
    var location: String? = null
    @SerializedName("blog")
    var blog: String? = null
    @SerializedName("public_repos")
    var publicRepos: Int = 0
}