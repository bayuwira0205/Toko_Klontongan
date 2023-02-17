package com.fyll.tokoklontongan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.fyll.tokoklontongan.data.AppDatabase
import com.fyll.tokoklontongan.data.entity.Produk

class tambahActivity : AppCompatActivity() {
    private lateinit var namaProduk: EditText
    private lateinit var hargaProduk: EditText
    private lateinit var stokProduk: EditText
    private lateinit var btnSimpan: Button
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)

        namaProduk = findViewById(R.id.etNamaProduk)
        hargaProduk = findViewById(R.id.et_harga)
        stokProduk = findViewById(R.id.etStok)
        btnSimpan = findViewById(R.id.btnSimpan)
        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent != null) {
            val id = intent.getInt("id", 0)
            var produk = database.produkDao().get(id)

            namaProduk.setText(produk.nama_produk)
            hargaProduk.setText(produk.harga_produk)
            stokProduk.setText(produk.stok_produk)
        }

        btnSimpan.setOnClickListener {
            if (namaProduk.text.isNotEmpty() && hargaProduk.text.isNotEmpty() && stokProduk.text.isNotEmpty()) {
                if (intent != null) {
                    database.produkDao().update(
                        Produk(
                            intent.getInt("id", 0),
                            namaProduk.text.toString(),
                            hargaProduk.text.toString(),
                            stokProduk.text.toString()
                        )
                    )
                    Toast.makeText(applicationContext, "Berhasil Diubah", Toast.LENGTH_SHORT).show()
                } else {
                    database.produkDao().insertAll(
                        Produk(
                            null,
                            namaProduk.text.toString(),
                            hargaProduk.text.toString(),
                            stokProduk.text.toString()

                        )
                    )
                    Toast.makeText(applicationContext, "Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                        .show()
                }
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Silahkan isi semua data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}