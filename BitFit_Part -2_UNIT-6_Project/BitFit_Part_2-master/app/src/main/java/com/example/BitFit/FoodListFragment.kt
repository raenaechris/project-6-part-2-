package com.example.BitFit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.BitFit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class FoodListFragment : Fragment() {

    // Declare variables
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val foods = mutableListOf<DisplayFood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Inflate the fragment's layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        // Get reference to the RecyclerView
        foodsRecyclerView = view.findViewById(R.id.fragment_recycler_view)

        // Set up the adapter for the RecyclerView
        val foodAdapter = FoodAdapter(view.context, foods)
        foodsRecyclerView.adapter = foodAdapter

        // Retrieve data from the database and update the RecyclerView
        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.foodDAO().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFood(
                        entity.name, entity.calories
                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }

        // Set up the layout manager for the RecyclerView
        foodsRecyclerView.layoutManager = LinearLayoutManager(view.context).also {
            val dividerItemDecoration = DividerItemDecoration(view.context, it.orientation)
            foodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        return view
    }

    // Create a companion object for instantiating the fragment
    companion object {
        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }
}
