package com.torrex.vcricket.activities.mainUi

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.collection.LLRBNode.Color
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.profileUi.ProfileActivity
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityMainDashboardDrawerBinding
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.fragmentsUI.home.HomeFragment
import com.torrex.vcricket.models.User
import com.torrex.vcricket.modules.BaseActivity

import com.torrex.vcricket.sharedpreference.UserSharedPreference


class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var binding: ActivityMainDashboardDrawerBinding
    private var mUser:User?=null


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
                R.id.nav_home,R.id.nav_gallery,R.id.nav_admin, //drawer fragments
                R.id.navigation_home, R.id.nav_my_contest, R.id.navigation_payment,R.id.navigation_notifications //bottom navigation fragments
            ),drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
        navView.setupWithNavController(navController)



        //Get User Data From firestore
        getUserData()

    }

    //Option menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_log_out -> {
                logOut()
            }
            R.id.action_user_profile -> {
                val intent=Intent(this,ProfileActivity::class.java)
                intent.putExtra(GlobalConstant.USER_MODEL_DATA,mUser)
                startActivity(intent)
            }
        }
            return super.onOptionsItemSelected(item)
    }


    private fun logOut() {
        val dialog =AlertDialog.Builder(this).setTitle("LogOut")
            .setMessage(R.string.message_log_out)
        dialog.setPositiveButton("Yes"){dialogInterface,which->
            showProgressDialog("Logging Out")
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        dialog.setNegativeButton("No"){dialogInterface,which->
            Log.v("Dialog","Dialog Dismissed")
        }
        dialog.setCancelable(false)
        dialog.show()

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

    //Get User Data
    private fun  getUserData(){
        val mUserId=FirebaseAuth.getInstance().currentUser!!.uid
        FireBaseUserDataBase().getUserFireStore(this,mUserId)
    }

    //Get USer Success
    fun getUserSuccess(user:User){
        mUser=user

        setUpUserProfile()

        if (user.profileCompleted==0){
            val intent=Intent(this,ProfileActivity::class.java)
            intent.putExtra(GlobalConstant.USER_MODEL_DATA,mUser)
            startActivity(intent)
        }
        else{
            UserSharedPreference(this).setUserSharedPref(mUser!!)
            val mSharedUser=UserSharedPreference(this).getUserSharedPref()
            Log.v("JSON_USER",mSharedUser.id)

        }
    }

    private fun setUpUserProfile() {
        //Disable and able Navigation View for User
        binding.navView.menu.findItem(R.id.nav_admin).isVisible = GlobalConstant.ADMIN_USER.contains(mUser!!.id)
        GlobalFunctions.loadUserPicture(this,mUser!!.image,binding.drawerLayout.findViewById(R.id.iv_drawer_profile_image))
        val userName:TextView=binding.drawerLayout.findViewById(R.id.tv_drawer_user_name)
        val userMobile:TextView=binding.drawerLayout.findViewById(R.id.tv_drawer_user_mobile)
        userMobile.text= mUser!!.mobile.toString()
        userName.text=mUser!!.firstName
    }

    //OnResume
    override fun onResume() {
        getUserData()
        super.onResume()
    }

    //OnBackPressed
    override fun onBackPressed() {
        //check for home fragment
        val fragment=this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        val currentFragment= fragment!!.childFragmentManager.fragments[0]
        if (currentFragment is HomeFragment){
            doubleBackToExit()
        }
        else{
            //TODO("navigate to home fragment first")
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            //navController.navigate(R.id.action_nav_to_navigation_home)
            navController.popBackStack()

        }
    }

}