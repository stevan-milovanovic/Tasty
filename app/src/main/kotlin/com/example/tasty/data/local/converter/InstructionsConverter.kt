package com.example.tasty.data.local.converter

import androidx.room.TypeConverter


/**
 * Type converter for recipe instructions list
 */
class InstructionsConverter {

	companion object {
		private const val SEPARATOR = "|"
	}

	@TypeConverter
	fun fromListToString(list: List<String>): String = list.joinToString(SEPARATOR)

	@TypeConverter
	fun fromStringToList(data: String): List<String> = data.split(SEPARATOR)

}
