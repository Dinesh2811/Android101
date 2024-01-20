package com.dinesh.android.remote.country.data

import com.apollographql.apollo3.ApolloClient
import com.dinesh.android.country_schema.GetCountriesQuery
import com.dinesh.android.country_schema.GetCountryQuery
import com.dinesh.android.remote.country.model.DetailedCountry
import com.dinesh.android.remote.country.model.SimpleCountry

class ApolloCountryClient {

    private val apollo = ApolloClient.Builder()
        .serverUrl("https://countries.trevorblades.com/graphql")
        .build()

    suspend fun getCountries(): List<SimpleCountry> {
        return apollo.query(GetCountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    suspend fun getCountry(code: String): DetailedCountry? {
        return apollo.query(GetCountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}