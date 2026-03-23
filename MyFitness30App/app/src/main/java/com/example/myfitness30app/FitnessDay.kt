package com.example.myfitness30app

import androidx.annotation.DrawableRes

data class FitnessDay(
    val dayNumber: Int,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    @DrawableRes val imageRes: Int
)