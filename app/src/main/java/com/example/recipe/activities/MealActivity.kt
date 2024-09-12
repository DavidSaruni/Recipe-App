package com.example.recipe.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.recipe.R
import com.example.recipe.databinding.ActivityMealBinding
import com.example.recipe.fragments.HomeFragment

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        getMealInfoFromIntent()
        setInformationInViews()
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingtoolbar.title = mealName

    }

    private fun getMealInfoFromIntent(){
        intent?. let {
            mealId = it.getStringExtra(HomeFragment.MEAL_ID)?:"Unknown Id"
            mealName = it.getStringExtra(HomeFragment.MEAL_NAME)?:"Unknown Name"
            mealThumb = it.getStringExtra(HomeFragment.MEAL_THUMB)?:"Unknown Thumb"
        }

    }
}