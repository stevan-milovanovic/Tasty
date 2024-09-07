package com.example.tasty.data.network.model

import com.example.tasty.data.local.model.Tag
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
    val id: Int,
    @Json(name = "display_name")
    val displayName: String,
    val name: String?
) {
    fun toLocal(): Tag = Tag(
        id,
        displayName,
        name,
    )
}