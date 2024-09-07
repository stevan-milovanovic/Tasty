package com.example.tasty.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Type converter for recipe instructions list
 */
class InstructionsConverter {

    private val moshi: Moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, String::class.java)
    private val adapter = moshi.adapter<List<String>>(listType)

    @TypeConverter
    fun fromJson(json: String?): List<String> = json?.let {
        try {
            adapter.fromJson(it) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    } ?: emptyList()

    @TypeConverter
    fun toJson(list: List<String>?): String = list?.let {
        try {
            adapter.toJson(it)
        } catch (e: Exception) {
            ""
        }
    } ?: ""
}
