package com.example.firebaseapp.ui.activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseapp.R
import com.example.firebaseapp.databinding.ActivityLoginBinding
import com.example.firebaseapp.utils.LoadingUtils
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var loadingUtils: LoadingUtils
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtils = LoadingUtils(this)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loadingUtils.show()

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loadingUtils.dismiss()
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, ProductDashboardActivity::class.java)
                        startActivity(intent)
                    } else {
                        loadingUtils.dismiss()
                        val errorMessage = task.exception?.message ?: "Login failed"
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }



        binding.loginRedirect.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.forgotPasswordText.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}