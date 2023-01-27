package com.example.testapp.api

import com.example.testapp.model.CakeModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("waracle_cake-android-client")
    fun getCakes():Observable<ArrayList<CakeModel>>

}