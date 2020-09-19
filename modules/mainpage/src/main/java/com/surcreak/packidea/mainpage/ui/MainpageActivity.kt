package com.surcreak.packidea.mainpage.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.navigation.fragment.FragmentNavigator
import com.surcreak.packidea.base.ui.activity.BaseActivity
import com.surcreak.packidea.mainpage.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainpageActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.mainpage_activity_main

    override fun onViewCreated(savedInstanceState: Bundle?) {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.graph.addDestination(
            FragmentNavigator(
                this,
                supportFragmentManager,
                R.id.nav_host_fragment
            ).createDestination().apply {
                id = R.id.nav_home
                className = "com.surcreak.packidea.homepage.ui.fragment.HomeFragment"
            })

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_tools,
                R.id.nav_share,
                R.id.nav_send
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
