package com.fyll.tokoklontongan.data.dao

import androidx.room.*
import com.fyll.tokoklontongan.data.entity.Produk

@Dao
interface ProdukDao {
    @Query ("SELECT * FROM produk ORDER BY nama_produk ASC")
    fun getAll(): List<Produk>

    @Query ("SELECT * FROM produk WHERE nama_produk LIKE '%' || :search || '%' ")
    fun searchNamaProduk(search: String):List<Produk>

    @Query ("SELECT * FROM produk WHERE id_produk IN (:produkIds)")
    fun loadAllByIds(produkIds: IntArray): List<Produk>

    @Query("SELECT * FROM produk WHERE id_produk= :id_produk")
    fun get(id_produk:Int): Produk

    @Insert
    fun insertAll( vararg produks: Produk)

    @Delete
    fun delete(produk: Produk)
    @Update
    fun update(produk: Produk)



}