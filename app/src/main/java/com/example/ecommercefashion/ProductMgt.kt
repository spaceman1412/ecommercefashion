package com.example.ecommercefashion

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommercefashion.databinding.ActivityProductMgtBinding
import com.example.ecommercefashion.models.ProductE
import com.example.ecommercefashion.models.ProductDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProductMgt : AppCompatActivity() {
    private lateinit var binding: ActivityProductMgtBinding
    private lateinit var database: DatabaseReference

    companion object {
        val TAG = "ProductActivity"
        private const val RC_SIGN_IN = 100

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductMgtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = FirebaseDatabase.getInstance().reference
        getListProduct()
        val btnCreateProduct: Button = binding.btnCreate
        btnCreateProduct.setOnClickListener {
            val uid = FirebaseAuth.getInstance().uid
            val ref = FirebaseDatabase.getInstance().getReference("/products/$uid")
            var name = binding.editTextTextPersonName.text.toString()
            var price = binding.editTextTextPersonName2.text.toString().toInt()
            var url = binding.editTextTextPersonName3.text.toString()
            var sex = binding.editTextTextPersonName4.text.toString()
            ref.setValue(ProductDTO(name, price, url, sex))
                .addOnCompleteListener {
                    Log.d(RegisterFragment.TAG,"Saved user to database")
                }
                .addOnFailureListener {
                    Log.d(RegisterFragment.TAG,it.message.toString())
                }
        }

    }
    private fun getListProduct() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/products/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val listProduct : ArrayList<ProductE?> = ArrayList()
                    it.children.forEach {
                        val item : ProductE? = it.getValue(ProductE::class.java)
                        Log.d("123456",item.toString())
                        listProduct.add(item)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("CheckOutActivity",error.message)
            }

        })
    }
}