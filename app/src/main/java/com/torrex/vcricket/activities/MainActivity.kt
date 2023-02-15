package com.torrex.vcricket.activities

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.torrex.vcricket.R
import com.torrex.vcricket.databinding.ActivityMainDashboardDrawerBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var binding: ActivityMainDashboardDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainDashboardDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SetUpActionBar
        setUpActionBar()


        //Setting drawer layout bottom Navigation and drawer nav view
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val bottomNavView: BottomNavigationView =binding.appBarMain.navViewBottom
        val navView: NavigationView =binding.navView

        //nav controller for fragment in activity
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        //appBarConfiguration for all fragment Id and drawer layout
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_gallery,R.id.nav_slideshow, //drawer fragments
                R.id.navigation_home, R.id.navigation_account, R.id.navigation_payment,R.id.navigation_notifications //bottom navigation fragments
            ),drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
        navView.setupWithNavController(navController)
    }

    //Option menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //Navigation View set Up
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //setupActionBar
    private fun setUpActionBar(){
        setSupportActionBar(binding.appBarMain.toolbarContentMain)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
}