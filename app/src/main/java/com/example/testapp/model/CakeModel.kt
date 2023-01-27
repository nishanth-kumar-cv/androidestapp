package com.example.testapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CakeModel(
    val title:String,
    val desc:String,
    val image:String
) : Parcelable
