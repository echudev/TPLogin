package com.ifts.tplogin


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifts.tplogin.databinding.ActivityMainBinding
import com.ifts.utils.hideKeyboard

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

        // get the root
        val rootLayout = binding.main

        // hide keyboard when touch outside
        rootLayout.setOnClickListener {
            hideKeyboard()
        }

        // use the binding to access toEditText & Button
        binding.btnLogin.setOnClickListener {
            val name = binding.editTextUser.text.toString()
            val password = binding.editTextPass.text.toString()

            var user: User? = null
            try {
                user = User(name, password)
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "Invalid user data", Toast.LENGTH_SHORT).show()
            }

            if (user != null) {
                // Create an explicit Intent to go to HomeActivity
                val intent = Intent(this, HomeActivity::class.java)

                // Inject the user value into the Intent
                intent.putExtra("user", user)

                // Init the new Activity
                startActivity(intent)
            }
        }
    }
}