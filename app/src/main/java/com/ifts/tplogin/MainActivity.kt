package com.ifts.tplogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifts.tplogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding para obtener id de los elementos de la vista
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // use the binding to access toEditText & Button
        binding.btnLogin.setOnClickListener {
            val user = binding.editTextUser.text.toString().toIntOrNull() ?: 0
            val password = binding.editTextPass.text.toString().toIntOrNull() ?: 0

            // Create an explicit Intent to go to ResultActivity
            val intent = Intent(this, HomeActivity::class.java)

            // Pass the sum value to the Intent
            intent.putExtra("user", user)

            // Init the new Activity
            startActivity(intent)
        }
    }
}