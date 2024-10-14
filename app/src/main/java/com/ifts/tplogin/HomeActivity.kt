package com.ifts.tplogin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.ifts.tplogin.databinding.ActivityHomeBinding
import com.ifts.tplogin.fragments.HomeFragment
import com.ifts.tplogin.fragments.ProfileFragment
import com.ifts.tplogin.fragments.SettingsFragment


class HomeActivity : AppCompatActivity() {

    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    private lateinit var binding: ActivityHomeBinding

    // ActionBarDrawerToggle para manejar la apertura/cierre del Navigation Drawer con el ícono de "hamburger".
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get intent sumResult value
        val user = intent.parcelable<User>("user") ?: User("Guest", "1234")

        // Show result on TextView "textGreetings"
        binding.toolbar.title = getString(R.string.user_welcome, user.name)

        // Configura la Toolbar para actuar como barra de acción.
        setupToolbar()

        // Configura el Navigation Drawer con el listener del ActionBarDrawerToggle.
        setupNavigationDrawer()

        // Gestiona el comportamiento del botón "back" para cerrar el drawer si está abierto.
        handleBackPress()

        // Si la actividad se crea por primera vez, carga el fragmento inicial (Home).
        if (savedInstanceState == null) {
            loadInitialFragment()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun setupNavigationDrawer() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,  // Texto que aparece cuando el drawer se abre
            R.string.navigation_drawer_close  // Texto que aparece cuando el drawer se cierra
        )

        // Añade el listener del DrawerToggle al DrawerLayout
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState() // Sincroniza el estado del ícono "hamburger" con el drawer.

        // Establece el listener para manejar la selección de ítems en el Navigation Drawer.
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    /**
     * Este método se encarga de manejar la selección de un elemento del menú del Navigation Drawer.
     * Dependiendo del ítem seleccionado, carga el fragmento correspondiente (Home, Profile o Settings).
     *
     * @param menuItem El ítem seleccionado en el Navigation Drawer.
     */
    private fun selectDrawerItem(menuItem: MenuItem) {
        // Carga el fragmento correspondiente basado en el ID del ítem seleccionado.
        val fragment: Fragment = when (menuItem.itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_profile -> ProfileFragment()
            R.id.nav_settings -> SettingsFragment()
            else -> HomeFragment() // Si no se reconoce el ítem, carga el fragmento Home por defecto.
        }

        // Reemplaza el fragmento en el contenedor del layout.
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        // Marca el ítem como seleccionado y actualiza el título de la Toolbar.
        menuItem.isChecked = true
        title = menuItem.title

        // Cierra el Navigation Drawer una vez que se selecciona un ítem.
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    /**
     * Configura el comportamiento del botón "back" para que, si el Navigation Drawer está abierto,
     * lo cierre en lugar de salir de la actividad. Si el drawer está cerrado, el comportamiento es el normal.
     */
    private fun handleBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Si el Drawer está abierto, ciérralo. Si está cerrado, sigue el comportamiento normal.
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    /**
     * Carga el fragmento inicial (HomeFragment) cuando se abre la actividad por primera vez.
     * También marca el ítem correspondiente en el Navigation Drawer como seleccionado.
     */
    private fun loadInitialFragment() {
        // Reemplaza el fragmento en el contenedor por el HomeFragment.
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        // Marca el ítem "Home" en el menú del drawer como seleccionado.
        binding.navView.setCheckedItem(R.id.nav_home)
    }

    /**
     * Este método se encarga de manejar las interacciones con los ítems del menú de opciones.
     * Si el ActionBarDrawerToggle puede manejar el evento (por ejemplo, abrir/cerrar el drawer),
     * retorna `true`, de lo contrario, delega el manejo al método padre.
     *
     * @param item El ítem seleccionado en el menú de opciones.
     * @return `true` si el evento fue manejado, de lo contrario, el valor retornado por el método padre.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}