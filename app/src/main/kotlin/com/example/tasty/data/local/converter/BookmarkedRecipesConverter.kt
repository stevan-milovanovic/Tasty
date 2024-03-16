package com.example.tasty.data.local.converter

import androidx.room.TypeConverter

/**
 * Type converter for bookmarked recipes set
 */
class BookmarkedRecipesConverter {

    companion object {
        private const val SEPARATOR = ", "
    }

    @TypeConverter
    fun fromSetToString(list: Set<Int>): String = list.joinToString(SEPARATOR)

    @TypeConverter
    fun fromStringToSet(data: String): Set<Int> {
        if (data.isBlank()) return emptySet()
        return data.split(SEPARATOR).map { it.toInt() }.toSet()
    }
}
