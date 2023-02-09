package com.heechan.barcoderepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Barcode::class], version = 1)
abstract class BarcodeDatabase : RoomDatabase() {
    abstract fun barcodeDao(): BarcodeDao

    companion object {
        @Volatile
        private var INSTANCE: BarcodeDatabase? = null
        fun getInstance(context: Context): BarcodeDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context,
                BarcodeDatabase::class.java,
                "barcode_database"
            ).fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}