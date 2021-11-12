package ru.dariaaa.biirr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import ru.dariaaa.biirr.databinding.ActivityMainBinding
import ru.dariaaa.biirr.di.ApplicationComponent

class MainActivity : AppCompatActivity() {


    lateinit var navController:NavController
    lateinit var binding: ActivityMainBinding
    lateinit var appComponent: ApplicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        appComponent = (application as App).appComponent
        appComponent.inject(this)



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController


        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}