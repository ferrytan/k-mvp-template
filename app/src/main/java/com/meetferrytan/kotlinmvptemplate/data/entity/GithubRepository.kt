package com.meetferrytan.kotlinmvptemplate.data.entity

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by ferrytan on 24/12/17.
 */

class GithubRepository {
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    @SerializedName("pushed_at")
    var pushedAt: Date? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
}
