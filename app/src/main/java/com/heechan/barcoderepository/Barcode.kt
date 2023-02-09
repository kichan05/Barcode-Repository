package com.heechan.barcoderepository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Barcode(
    @PrimaryKey
    val id : String,
    val name : String,
    val description : String,
)
