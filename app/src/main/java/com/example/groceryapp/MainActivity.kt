package com.example.groceryapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() ,GroceryRvAdapter.GroceryItemClickInterface{
    private var binding : ActivityMainBinding? = null
    lateinit var list: List<GroceryItems>
    lateinit var groceryAdapter : GroceryRvAdapter
    lateinit var groceryViewModel: GroceryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        list = ArrayList<GroceryItems>()
        groceryAdapter = GroceryRvAdapter(list,this)

        binding!!.idRvItems.layoutManager = LinearLayoutManager(this)
        binding!!.idRvItems.adapter = groceryAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))

        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this,factory)[GroceryViewModel::class.java]


        groceryViewModel.getAllGroceryItems().observe(this, Observer {
            groceryAdapter.list = it
            groceryAdapter.notifyDataSetChanged()
        })

        binding!!.idFabAdd.setOnClickListener {

            openDialog()

        }
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id._idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id._idBtnAdd)
        val itemEdt = dialog.findViewById<EditText>(R.id._idEditItemName)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id._idEditItemQuantity)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id._idEditItemPrice)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName : String = itemEdt.text.toString()
            val itemPrice : String = itemPriceEdt.text.toString()
            val itemQuantity : String = itemQuantityEdt.text.toString()
            val qty : Int = itemPrice.toInt()
            val pr : Int = itemPrice.toInt()

            if (itemName.isNotEmpty()&&itemPrice.isNotEmpty()&&itemQuantity.isNotEmpty()){
                val items = GroceryItems(itemName,qty,pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item inserted",Toast.LENGTH_SHORT).show()
                groceryAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext,"Please enter all the data",Toast.LENGTH_SHORT).show()
            }

        }
        dialog.show()

    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted",Toast.LENGTH_SHORT).show()
    }
}