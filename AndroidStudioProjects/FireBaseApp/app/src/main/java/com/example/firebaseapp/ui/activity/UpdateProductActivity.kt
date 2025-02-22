package com.example.firebaseapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.ActivityUpdateProductBinding
import com.example.firebaseapp.repository.ProductRepositoryImpl
import com.example.firebaseapp.viewModel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProductBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the repository and ViewModel
        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        // Get the product ID passed via Intent
        val productId: String? = intent.getStringExtra("productId")

        // Observe the product LiveData
        productViewModel.getProductById(productId.toString())
        productViewModel.product.observe(this) { product ->
            if (product != null) {
                // Populate fields with product details
                binding.updateProductDescription.setText(product.productDesc)
                binding.updateProductPrice.setText(product.price)
                binding.updateProductName.setText(product.productName)
            } else {
                Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle the update button click
        binding.updateButton.setOnClickListener {
            val productName = binding.updateProductName.text.toString()
            val price = binding.updateProductPrice.text.toString()
            val desc = binding.updateProductDescription.text.toString()

            if (productName.isNotBlank() && price.isNotBlank()&& desc.isNotBlank()) {
                val updatedMap = mutableMapOf<String, Any>(
                    "productName" to productName,
                    "productDesc" to desc,
                    "price" to price
                )

                productViewModel.updateProduct(productId.toString(), updatedMap) { success, message ->
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    if (success) {
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}