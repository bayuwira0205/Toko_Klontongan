package com.fyll.tokoklontongan

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fyll.tokoklontongan.adapter.ProdukAdapter
import com.fyll.tokoklontongan.data.AppDatabase
import com.fyll.tokoklontongan.data.entity.Produk
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var btnTambah: FloatingActionButton
    private var list = mutableListOf<Produk>()
    private lateinit var adapter: ProdukAdapter
    private lateinit var database: AppDatabase
    private lateinit var inputSearch: TextInputEditText
    private lateinit var total: TextView
    private lateinit var jml_stok: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.rv_produk)
        btnTambah = findViewById(R.id.btn_tambah)
        inputSearch = findViewById(R.id.textInputEditText)
        database = AppDatabase.getInstance(applicationContext)
        adapter = ProdukAdapter(list)

        //Mengambil Fungsi dari Adapter
        adapter.setListener(object : ProdukAdapter.OnAdapterListener {

            //Fungsi kurangi Stok
            override fun onClickKurang(position: Int) {
                jml_stok = findViewById(R.id.tv_jumlahStok)
                var nilai = list[position].stok_produk.toString().toInt()
                var nilaibaru = nilai - 1
                list[position].stok_produk = nilaibaru.toString()
                database.produkDao().update(list[position])
                getData()
            }

            //Fungsi menambah Stok
            override fun onClickTambah(position: Int) {
                jml_stok = findViewById(R.id.tv_jumlahStok)
                var nilai = list[position].stok_produk.toString().toInt()
                var nilaibaru = nilai + 1
                list[position].stok_produk = nilaibaru.toString()
                database.produkDao().update(list[position])
                getData()

            }

            ////Fungsi klik tampilan list (Dialog)
            override fun onClickTitle(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(list[position].nama_produk)
                dialog.setItems(
                    R.array.pilihan_item,
                    DialogInterface.OnClickListener { dialog, which ->

                        //Fungsi Ubah Data (akses string)
                        if (which == 0) {
                            val intent = Intent(this@MainActivity, tambahActivity::class.java)
                            intent.putExtra("id", list[position].id_produk)
                            startActivity(intent)

                            //Fungsi Hapus Data
                        } else if (which == 1) {
                            database.produkDao().delete(list[position])
                            Toast.makeText(
                                applicationContext,
                                "Berhasil Dihapus",
                                Toast.LENGTH_SHORT
                            ).show()
                            totalProduk()

                        } else {
                            dialog.dismiss()
                        }

                    })
                val dialogView = dialog.create()
                dialogView.show()
            }
        })

        recyclerview.adapter = adapter
        recyclerview.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )

        //Aksi Tombol Tambah Data
        btnTambah.setOnClickListener {
            startActivity(Intent(this, tambahActivity::class.java))
        }

        //Aksi Cari Data
        inputSearch.setOnKeyListener { view, i, keyEvent ->
            if (inputSearch.text!!.isNotEmpty()) {
                searchData(inputSearch.text.toString())
            } else {
                getData()
            }

            false
        }


    }

    //fungsi onResume
    override fun onResume() {
        super.onResume()
        totalProduk()
        getData()

    }

    //fungsi menampilkan data
    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        list.clear()
        list.addAll(database.produkDao().getAll())
        adapter.notifyDataSetChanged()

    }

    //fungsi cari
    @SuppressLint("NotifyDataSetChanged")
    fun searchData(search: String) {
        list.clear()
        list.addAll(database.produkDao().searchNamaProduk(search))
        adapter.notifyDataSetChanged()
    }

    //fungsi menampilkan total produk
    @SuppressLint("SetTextI18n")
    fun totalProduk() {
        total = findViewById(R.id.tv_totalProduk)
        val totalProduk = database.produkDao().getAll().size
        total.text = "$totalProduk item"

        getData()
    }
}