package com.fyll.tokoklontongan.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Produk(
    @PrimaryKey(autoGenerate = true) var id_produk: Int?=null,
    @ColumnInfo(name = "nama_produk") var nama_produk:String?,
    @ColumnInfo(name = "harga_produk") var harga_produk:String?,
    @ColumnInfo(name = "stok_produk") var stok_produk:String?,
)
