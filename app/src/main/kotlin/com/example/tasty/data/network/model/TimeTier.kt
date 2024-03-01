package com.example.tasty.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimeTier(
	@Json(name = "display_tier")
	val totalTimeNeeded: String
)
