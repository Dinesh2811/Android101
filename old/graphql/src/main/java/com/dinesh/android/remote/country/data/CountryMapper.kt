package com.dinesh.android.remote.country.data

import com.dinesh.android.country_schema.GetCountriesQuery
import com.dinesh.android.country_schema.GetCountryQuery
import com.dinesh.android.remote.country.model.DetailedCountry
import com.dinesh.android.remote.country.model.SimpleCountry

fun GetCountryQuery.Country.toDetailedCountry() : DetailedCountry {
    return DetailedCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No Capital",
        currency = currency ?: "No Currency",
        languages = languages.map { it.name },
        continent = continent.name
    )
}

fun GetCountriesQuery.Country.toSimpleCountry() : SimpleCountry {
    return SimpleCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No Capital"
    )
}