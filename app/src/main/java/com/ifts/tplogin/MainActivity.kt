package com.ifts.tplogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifts.tplogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding to obtain id from view elements
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
            val user = binding.editTextUser.text.toString()
            val password = binding.editTextPass.text.toString()

            // Create an explicit Intent to go to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)

            // Inject the user value into the Intent
            intent.putExtra("user", user)

            // Init the new Activity
            startActivity(intent)
        }
    }
}