package com.example.recipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.pojo.Meal
import com.example.recipe.pojo.MealList
import com.example.recipe.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() !=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    // dif between livedata and mutablelivedata
    // mutablelivedata can be changed - read and write
    // livedata cannot be changed - read only
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
}