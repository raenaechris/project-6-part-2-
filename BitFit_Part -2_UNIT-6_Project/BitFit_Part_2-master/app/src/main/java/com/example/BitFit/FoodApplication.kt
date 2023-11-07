package com.example.BitFit

import android.app.Application

class FoodApplication : Application() {
    val db by lazy { HealthMetricsDatabase.getInstance(this) }
}