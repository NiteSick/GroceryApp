package com.example.groceryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.databinding.GroceryRvItemBinding

class GroceryRvAdapter(
    var list: List<GroceryItems>,
    val groceryItemClickInterface: GroceryItemClickInterface
    ): RecyclerView.Adapter<GroceryRvAdapter.GroceryViewHolder>() {


    inner class GroceryViewHolder( val binding : GroceryRvItemBinding) : RecyclerView.ViewHolder(binding.root){

    }


    interface GroceryItemClickInterface{
        fun onItemClick(groceryItems: GroceryItems)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        return GroceryViewHolder(GroceryRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.binding.idTvItemName.text = list[position].itemName
        holder.binding.idTvQuantity.text = list[position].itemQuantity.toString()
        holder.binding.idTvRate.text = "Rs. "+list[position].itemPrice.toString()
        val itemTotal : Int = list[position].itemPrice * list[position].itemQuantity

        holder.binding.idTvTotalAmt.text = "Rs. $itemTotal"
        holder.binding.idTvDelete.setOnClickListener {
            groceryItemClickInterface.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}