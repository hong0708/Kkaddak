package com.ssafy.kkaddak.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ActivityMainBinding
import com.ssafy.kkaddak.presentation.songlist.SongService
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var serviceIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceIntent = Intent(this, SongService::class.java)
        startService(serviceIntent)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        val graphInflater = navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.navigation_main)

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            itemIconTintList = null
            selectedItemId = R.id.blank
        }
        binding.fabHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        navController.graph = navGraph

        val intent = Intent(this, SongService::class.java)
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent)
    }

    fun hideBottomNavigation(state: Boolean) {
        when(state) {
            true -> {
                binding.bottomNavigation.visibility = View.GONE
                binding.fabHome.visibility = View.GONE
            }
            else -> {
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.fabHome.visibility = View.VISIBLE
            }
        }
    }
}