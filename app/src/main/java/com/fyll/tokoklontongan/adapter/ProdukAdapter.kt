package com.fyll.tokoklontongan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fyll.tokoklontongan.R
import com.fyll.tokoklontongan.data.entity.Produk

class ProdukAdapter(var list: List<Produk>) : RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {
    private lateinit var listener: OnAdapterListener

    interface OnAdapterListener {
        fun onClickKurang(position: Int)
        fun onClickTambah(position: Int)
        fun onClickTitle(position: Int)
    }

    fun setListener(listener: OnAdapterListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var namaProduk: TextView
        var hargaProduk: TextView
        var stokProduk: TextView
        var kurang: ImageButton
        var tambah: ImageButton

        init {
            namaProduk = view.findViewById(R.id.tv_namaProduk)
            hargaProduk = view.findViewById(R.id.tv_hargaProduk)
            stokProduk = view.findViewById(R.id.tv_jumlahStok)

            kurang = view.findViewById(R.id.img_kurang)
            tambah = view.findViewById(R.id.img_tambah)

            view.setOnClickListener {
                listener.onClickTitle(layoutPosition)
            }

            kurang.setOnClickListener {
                listener.onClickKurang(layoutPosition)
            }

            tambah.setOnClickListener {
                listener.onClickTambah(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_produk, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.namaProduk.text = list[position].nama_produk
        holder.hargaProduk.text = list[position].harga_produk
        holder.stokProduk.text = list[position].stok_produk

    }

}