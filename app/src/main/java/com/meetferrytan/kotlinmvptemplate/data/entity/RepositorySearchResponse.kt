package com.meetferrytan.kotlinmvptemplate.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by ferrytan on 24/12/17.
 */

class RepositorySearchResponse {
    @SerializedName("total_count")
    var totalCount: Int = 0
    @SerializedName("incomplete_results")
    var isIncompleteResults: Boolean = false
    @SerializedName("items")
    var items: List<GithubRepository>? = null
}
