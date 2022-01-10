package br.com.ewapps.newsapp.model

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class AssetParamTypeUrl : NavType<Url>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Url? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Url {
        return Gson().fromJson(value, Url::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Url) {
        bundle.putParcelable(key, value)
    }
}