package com.levit.exerciciomenumagalu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.levit.exerciciomenumagalu.databinding.ActivityMainBinding
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.activity.addCallback

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Modo imersivo
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.statusBars())

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_cart -> openFragment(CartFragment())
                R.id.bottom_profile -> openFragment(ProfileFragment())
                R.id.bottom_menu -> openFragment(MenuFragment())
            }
            true
        }

        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Categorias", Toast.LENGTH_SHORT).show()
        }

        onBackPressedDispatcher.addCallback(this) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_electronics -> openFragment(ElectronicsFragment())
            R.id.nav_fashion -> openFragment(FashionFragment())
            R.id.nav_games -> openFragment(GamesFragment())
            R.id.nav_casa -> Toast.makeText(this, "Casa", Toast.LENGTH_SHORT).show()
            R.id.nav_beauty -> Toast.makeText(this, "Beleza", Toast.LENGTH_SHORT).show()
            R.id.nav_assinaturas -> Toast.makeText(this, "Assinaturas", Toast.LENGTH_SHORT).show()
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}