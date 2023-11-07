/*

This fragment displays the user's food data statistics in the dashboard view.
It calculates the minimum, maximum, and average calories of all the logged foods.
It also provides a button to clear all logged food data from the local database.
 */

package com.example.BitFit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private val foods = mutableListOf<DisplayFood>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Initialize UI components
        val averageCalories = view.findViewById<TextView>(R.id.average_caloriesTV)
        val minCalories = view.findViewById<TextView>(R.id.minimum_calorieTV)
        val maxCalories = view.findViewById<TextView>(R.id.maximum_calorieTV)
        val clearDataButton = view.findViewById<Button>(R.id.clear_dataBT)

        val foodAdapter = FoodAdapter(view.context, foods)

        // Initialize calorie statistics variables
        var minCal = 0
        var maxCal = 0
        var avgCal = 0
        var totalCalories = 0

        // Set initial values for UI components
        averageCalories.text = avgCal.toString()
        minCalories.text = minCal.toString()
        maxCalories.text = maxCal.toString()

        // Observe database changes using Kotlin coroutines and update UI accordingly
        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.foodDAO().getAll()
                .collect { databaseList ->
                    databaseList.map { entity ->
                        DisplayFood(
                            entity.name, entity.calories
                        )
                    }.also { mappedList ->
                        foods.clear()
                        foods.addAll(mappedList)
                        if (foods.size > 0) {
                            // Calculate calorie statistics
                            minCal = foods.get(0).calories!!.toInt()
                            for (i in foods) {
                                if (i.calories!! > maxCal) {
                                    maxCal = i.calories.toInt()
                                } else if (i.calories < minCal) {
                                    minCal = i.calories.toInt()
                                }
                                totalCalories += i.calories.toInt()
                            }

                            // Update UI components with calculated statistics
                            maxCalories.text = maxCal.toString()
                            minCalories.text = minCal.toString()
                            avgCal = totalCalories / foods.size
                            averageCalories.text = avgCal.toString()
                            foodAdapter.notifyDataSetChanged()
                        }
                    }
                }
        }

        // Set click listener for the clear data button to delete all logged food data
        clearDataButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (activity?.application as FoodApplication).db.foodDAO().deleteAll()
            }
        }

        return view
    }

}