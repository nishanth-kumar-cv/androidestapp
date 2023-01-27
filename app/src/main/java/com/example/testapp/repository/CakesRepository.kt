package com.example.testapp.repository

import com.example.testapp.api.ApiHelper
import javax.inject.Inject

class CakesRepository
    @Inject constructor(private val apiHelper: ApiHelper) {
    fun getCakes() = apiHelper.getCakes()
}