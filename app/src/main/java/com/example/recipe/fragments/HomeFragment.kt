package com.example.recipe.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipe.activities.MealActivity
import com.example.recipe.databinding.FragmentHomeBinding
import com.example.recipe.pojo.Meal
import com.example.recipe.viewModel.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal

    companion object{
        const val MEAL_ID = "com.example.recipe.fragments.idMeal"
        const val MEAL_NAME = "com.example.recipe.fragments.nameMeal"
        const val MEAL_THUMB="com.example.easyfood.ui.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer { t ->
            t?.let {
                Glide.with(this@HomeFragment)
                    .load(it.strMealThumb)
                    .into(binding.imgRandomMeal)
                this.randomMeal = it
            }
        })
    }

}