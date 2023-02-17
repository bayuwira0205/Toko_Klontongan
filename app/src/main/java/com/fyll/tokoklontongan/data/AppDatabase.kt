package com.fyll.tokoklontongan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fyll.tokoklontongan.data.dao.ProdukDao
import com.fyll.tokoklontongan.data.entity.Produk

@Database(entities = [Produk::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun produkDao():ProdukDao

    companion object{
        private var instance: AppDatabase?=null

        fun getInstance(context: Context):AppDatabase{

            if (instance==null){
                instance= Room.databaseBuilder(context,AppDatabase::class.java, "produk-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }

}