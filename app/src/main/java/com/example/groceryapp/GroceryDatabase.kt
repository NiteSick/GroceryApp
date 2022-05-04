package com.example.groceryapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GroceryItems::class], version = 1)
abstract class GroceryDatabase : RoomDatabase(){

    abstract fun getGroceryDao() : GroceryDao

    companion object {
        @Volatile
        private var instance: GroceryDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }


        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GroceryDatabase::class.java,
            "Grocery.db",
        ).build()

    }
}