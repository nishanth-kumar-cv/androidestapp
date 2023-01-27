package com.example.testapp.api

import com.example.testapp.model.CakeModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper {
    override fun getCakes(): Observable<ArrayList<CakeModel>> {
        return apiService.getCakes()
    }
}