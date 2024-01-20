package com.dinesh.android.local.v1

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.dinesh.android.v1.AddPersonMutation
import com.dinesh.android.v1.DeletePersonsByNameMutation
import com.dinesh.android.v1.FetchDataQuery
import com.dinesh.android.v1.FetchGreetingQuery
import com.dinesh.android.v1.FetchNumbersQuery
import com.dinesh.android.v1.FetchPersonQuery
import com.dinesh.android.v1.FetchPersonsQuery
import com.dinesh.android.v1.SearchPersonsByAgeQuery
import com.dinesh.android.v1.SearchPersonsByNameAgeQuery
import com.dinesh.android.v1.SearchPersonsByNameQuery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApiClient {
    private val TAG = "log_" + ApiClient::class.java.name.split(ApiClient::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private val loggingInterceptor = HttpLoggingInterceptor {
//            message -> Log.d("log_ApiClient", message)
    }.apply { level = HttpLoggingInterceptor.Level.BODY }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val apollo = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:3000/graphql/")
        .okHttpClient(httpClient)
        .build()

    suspend fun getData(): Data? {
        return try {
            return apollo.query(FetchDataQuery())
                .execute()
                .data
                ?.toFetchData()
        } catch (e: Exception) {
            Log.e(TAG, "getData: ${e.message}")
            null
        }
    }

    suspend fun getGreeting(): Greeting? {
        return try {
            return apollo.query(FetchGreetingQuery())
                .execute()
                .data
                ?.toFetchGreeting()
//                ?.hello.toString()
        } catch (e: Exception) {
            Log.e(TAG, "getGreeting: ${e.message}")
            null
        }
    }

    suspend fun getNumbers(): Numbers? {
        return try {
            return apollo.query(FetchNumbersQuery())
                .execute()
                .data
                ?.toFetchNumbers()
        } catch (e: Exception) {
            Log.e(TAG, "getNumbers: ${e.message}")
            null
        }
    }

    suspend fun getPersons(): Persons? {
        return try {
            return apollo.query(FetchPersonsQuery())
                .execute()
                .data
                ?.toFetchPersons()
        } catch (e: Exception) {
            Log.e(TAG, "getNumbers: ${e.message}")
            null
        }
    }

    suspend fun getPerson(name: String): Person? {
        return try {
            val response = apollo.query(FetchPersonQuery(name)).execute()
            response.data?.person?.toFetchPerson()
        } catch (e: Exception) {
            Log.e(TAG, "getPerson: ${e.message}")
            null
        }
    }

    suspend fun getPersonsByNameAge(name: String, age: Int): Persons? {
        return try {
            val response = apollo.query(SearchPersonsByNameAgeQuery(name, age)).execute()
//            val x : List<SearchPersonsQuery.SearchPerson?>? = response.data?.toFetchPersons()
            response.data?.toFetchPersonsByNameAge()
        } catch (e: Exception) {
            Log.e(TAG, "getPerson: ${e.message}")
            null
        }
    }

    suspend fun getPersonsByName(name: String): Persons? {
        return try {
            val response = apollo.query(SearchPersonsByNameQuery(name)).execute()
            response.data?.toFetchPersonsByName()
        } catch (e: Exception) {
            Log.e(TAG, "getPerson: ${e.message}")
            null
        }
    }

    suspend fun getPersonsByAge(age: Int): Persons? {
        return try {
            val response = apollo.query(SearchPersonsByAgeQuery(age)).execute()
            response.data?.toFetchPersonsByAge()
        } catch (e: Exception) {
            Log.e(TAG, "getPerson: ${e.message}")
            null
        }
    }

    suspend fun postData(person: Person) {
        val addPersonMutation = AddPersonMutation(
            name = person.name!!,
            age = person.age!!,
            street = person.address?.street!!,
            city = person.address?.city!!,
            country = person.address?.country!!
        )

        try {
            val response = apollo.mutation(addPersonMutation)
            val addedPerson = response.execute().data?.addPerson
            Log.e(TAG, "Added person: $addedPerson")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to add person ${e.message}")
        }
    }

    suspend fun deletePersonsByName(name: List<String>): Persons? {
        return try {
            val response = apollo.mutation(DeletePersonsByNameMutation(name)).execute()
            response.data?.toDeletePersonsByName()
        } catch (e: Exception) {
            Log.e(TAG, "getPerson: ${e.message}")
            null
        }
    }
}
