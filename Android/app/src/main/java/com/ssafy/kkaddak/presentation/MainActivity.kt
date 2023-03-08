package com.ssafy.kkaddak.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        val graphInflater = navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.navigation_main)

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.itemIconTintList = null
        binding.fabHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.visibility = when (destination.id) {
                R.id.songListFragment -> View.VISIBLE
                R.id.walletFragment -> View.VISIBLE
                R.id.marketFragment -> View.VISIBLE
                R.id.profileFragment -> View.VISIBLE
                else -> View.VISIBLE
            }
        }

        navController.graph = navGraph
    }
}