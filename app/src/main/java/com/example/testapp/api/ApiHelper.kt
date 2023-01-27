package com.example.testapp.api

import com.example.testapp.model.CakeModel
import io.reactivex.rxjava3.core.Observable

interface ApiHelper {
    fun getCakes():Observable<ArrayList<CakeModel>>
}