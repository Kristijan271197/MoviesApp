package com.invictastudios.moviesapp.core.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatabaseConverters {

    @TypeConverter
    fun fromStringList(value: List<String>): String { // fromStringList: Converts a List<String> to a JSON string before storing in the database.
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> { // toStringList: Converts a JSON string back to a List<String> when reading from the database.
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}