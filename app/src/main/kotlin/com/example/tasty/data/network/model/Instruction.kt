package com.example.tasty.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Instruction(
    @Json(name = "display_text")
    val text: String
)
