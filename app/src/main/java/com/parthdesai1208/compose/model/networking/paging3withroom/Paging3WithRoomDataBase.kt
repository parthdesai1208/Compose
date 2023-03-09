package com.parthdesai1208.compose.model.networking.paging3withroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Article::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(SourceConverter::class)
abstract class Paging3WithRoomDataBase : RoomDatabase() {

    abstract fun getPaging3WithRoomDataBaseDao(): Paging3WithRoomDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: Paging3WithRoomDataBase? = null

        fun getDatabase(context: Context): Paging3WithRoomDataBase {
            val tempInstance = INSTANCE

//            check if there is any existing instance is present for our room database
//            if there exist an existing instance then we'll return that instance
            if (tempInstance != null) {
                return tempInstance
            }

//            If there is no any instance present for our database then we'll create a new instance
//            WHY SYNCHRONIZED ?? --> Because everything inside the synchronized block will be protected
//            by concurrent execution on multiple threads
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Paging3WithRoomDataBase::class.java,
                    "Article_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}