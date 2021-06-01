package com.panikga.healthio.ui.main.home.category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panikga.healthio.data.local.entity.Category
import com.panikga.healthio.databinding.ItemCategoryBinding
import com.panikga.healthio.ui.main.hospital.HospitalListActivity

class CategoryAdapter(private val listCategory: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {


    private lateinit var mcontext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mcontext = parent.context
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(listCategory[position])
    }

    override fun getItemCount(): Int = listCategory.size

    inner class CategoryHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category){
            with(binding){
                Glide.with(itemView.context)
                    .load(category.categoryImage)
                    .into(categoryImage)
                itemView.setOnClickListener{
                    val dataCategory = Category(
                        category.categoryName,
                        category.categoryImage
                    )
                    Toast.makeText(mcontext, category.categoryName, Toast.LENGTH_SHORT).show()
                    val i = Intent(mcontext, HospitalListActivity::class.java)
                    i.putExtra(HospitalListActivity.EXTRA_HOSPITAL, dataCategory)
                    mcontext.startActivity(i)
                }
            }
        }
    }
}