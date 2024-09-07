package com.example.tasty.data.network.model

import com.example.tasty.data.local.model.Recipe
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Int,
    val name: String,
    val description: String?,
    @Json(name = "original_video_url")
    val videoUrl: String?,
    val keywords: String?,
    @Json(name = "thumbnail_url")
    val thumbnailUrl: String,
    @Json(name = "total_time_tier")
    val timeTier: TimeTier?,
    val instructions: List<Instruction>?,
    val tags: List<Tag>
) {
    fun toLocal(): Recipe = Recipe(
        id,
        name,
        description,
        thumbnailUrl,
        videoUrl,
        keywords,
        timeTier?.totalTimeNeeded,
        instructions?.map { it.text }
    )
}
