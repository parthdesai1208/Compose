package com.parthdesai1208.compose.model.networking.paging3withroom

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson


@Entity(tableName = "Article")
data class Article(
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val urlToImage: String?,
    var page: Int?,
)

class SourceConverter {
    @TypeConverter
    fun SourceToString(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun StringToSource(string: String): Source {
        return Gson().fromJson(string, Source::class.java)
    }
}