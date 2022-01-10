package br.com.ewapps.newsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Url(
    val url: String
) : Parcelable
