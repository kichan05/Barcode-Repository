package com.heechan.barcoderepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BarcodeDao {
    @Query("SELECT * FROM Barcode")
    fun getAll() : List<Barcode>

    @Insert
    fun insertAll(vararg datas : Barcode)
}