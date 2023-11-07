package com.example.BitFit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.BitFit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the activity layout using the ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set the initial fragment to display
        replaceFragment(FoodListFragment())

        // Initialize the fragments to use
        val foodListFragment: Fragment = FoodListFragment()
        val dashboardFragment: Fragment = DashboardFragment()

        // Initialize the Bottom Navigation Bar View
        val bottomNavigationBarView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set the selected listener for the Bottom Navigation Bar View
        bottomNavigationBarView.setOnItemSelectedListener { item ->
            lateinit var thisFragment: Fragment

            // Determine which fragment to display based on the selected item
            when (item.itemId) {
                R.id.log -> thisFragment = foodListFragment
                R.id.dashboard -> thisFragment = dashboardFragment
            }

            // Replace the current fragment with the selected fragment
            replaceFragment(thisFragment)
            true
        }

        // Set the initial selected item for the Bottom Navigation Bar View
        bottomNavigationBarView.selectedItemId = R.id.log

        // Set the click listener for the "Add New" button
        findViewById<Button>(R.id.addNew_BT).setOnClickListener {
            // Start the DetailActivity when the button is clicked
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

    }

    // Function to replace the current fragment with a new fragment
    private fun replaceFragment(thisFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_try, thisFragment)
        fragmentTransaction.commit()
    }
}
