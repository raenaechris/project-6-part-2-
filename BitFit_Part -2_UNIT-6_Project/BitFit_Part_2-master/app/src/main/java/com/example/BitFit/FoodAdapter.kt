// This is a custom adapter for displaying the food list in the DashboardFragment RecyclerView.
// The adapter takes in a context and a list of DisplayFood objects.
// DisplayFood is a custom data class with a food name and calorie count.
// The adapter is responsible for inflating the layout for each item in the list and binding the data to the views.

package com.example.BitFit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private val context: Context, private val foods: List<DisplayFood>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {


    // Create a ViewHolder object for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fit_list, parent, false)
        return ViewHolder(view)
    }

    // Bind the data to the views in the ViewHolder object
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }

    // Return the size of the list
    override fun getItemCount() = foods.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val foodTextView = itemView.findViewById<TextView>(R.id.name)
        private val caloriesTextView = itemView.findViewById<TextView>(R.id.calories)

        // Set the OnClickListener for the ViewHolder object
        init {
            itemView.setOnClickListener(this)
        }

        // Bind the data to the views in the ViewHolder object
        fun bind(food: DisplayFood) {
            foodTextView.text = food.foodName
            caloriesTextView.text = food.calories.toString()
        }

        // Handle clicks on the ViewHolder object
        override fun onClick(v: View?) {
            // Do nothing
        }
    }

}