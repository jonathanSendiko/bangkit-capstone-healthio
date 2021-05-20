package com.panikga.healthio.ui.seeall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.panikga.healthio.R
import com.panikga.healthio.data.local.entity.Hospital

class ListHospitalAdapter(private val listHospital: ArrayList<Hospital>): RecyclerView.Adapter<ListHospitalAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHospitalAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_hospital, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listHospital.size
    }

    override fun onBindViewHolder(holder: ListHospitalAdapter.ListViewHolder, position: Int) {
        val hospital = listHospital[position]
        holder.name.text = hospital.nama
        holder.alamat.text = hospital.alamat
//        holder.lat.text = hospital.lat
//        holder.lon.text = hospital.long
        holder.ratingAvg.text = hospital.rating_avg
        holder.ratingCount.text = hospital.rating_count
//        holder.kelas.text = hospital.kelas
//        holder.jenis.text = hospital.jenis
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listHospital[holder.adapterPosition])
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.nama)
        var alamat: TextView = itemView.findViewById(R.id.alamat)
//        var lat: TextView = itemView.findViewById(R.id.lat)
//        var lon: TextView = itemView.findViewById(R.id.lon)
        var ratingAvg: TextView = itemView.findViewById(R.id.rating_avg)
        var ratingCount: TextView = itemView.findViewById(R.id.rating_count)
//        var kelas: TextView = itemView.findViewById(R.id.kelas)
//        var jenis: TextView = itemView.findViewById(R.id.jenis)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Hospital)
    }
}