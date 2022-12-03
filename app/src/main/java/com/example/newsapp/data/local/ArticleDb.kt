package com.example.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsapp.data.models.Article


@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDb : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao


    companion object {
        @Volatile
        private var instance: ArticleDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDb::class.java,
                "fav_articles_db.db",
            ).allowMainThreadQueries().build()
    }
}