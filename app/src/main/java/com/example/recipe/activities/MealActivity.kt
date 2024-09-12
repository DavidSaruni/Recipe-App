package com.example.recipe.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipe.R
import com.example.recipe.databinding.ActivityMealBinding
import com.example.recipe.fragments.HomeFragment
import com.example.recipe.pojo.Meal
import com.example.recipe.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel // instance of the view model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply window insets to handle edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mealMvvm = ViewModelProvider(this).get(MealViewModel::class.java)

        getMealInfoFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailLiveData()

        onYoutubeImgClick()
    }

    private fun onYoutubeImgClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        }
    }

    private fun observeMealDetailLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this) { meal ->
            meal?.let {
                onResponseCase()
                binding.tvCategory.text = "Category: ${meal.strCategory}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvInstructions.text = meal.strInstructions

                youtubeLink = meal.strYoutube
            }
        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingtoolbar.title = mealName
        binding.collapsingtoolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        binding.collapsingtoolbar.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.white)
        )
    }

    private fun getMealInfoFromIntent() {
        intent?.let {
            mealId = it.getStringExtra(HomeFragment.MEAL_ID) ?: "Unknown Id"
            mealName = it.getStringExtra(HomeFragment.MEAL_NAME) ?: "Unknown Name"
            mealThumb = it.getStringExtra(HomeFragment.MEAL_THUMB) ?: "Unknown Thumb"
        }
    }

    private fun loadingCase() {
        binding.buttonAddToFavorite.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun onResponseCase() {
        binding.buttonAddToFavorite.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        binding.progressbar.visibility = View.INVISIBLE
    }
}
