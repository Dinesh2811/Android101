package com.dinesh.android.local.v1

import com.dinesh.android.v1.DeletePersonsByNameMutation
import com.dinesh.android.v1.FetchDataQuery
import com.dinesh.android.v1.FetchGreetingQuery
import com.dinesh.android.v1.FetchNumbersQuery
import com.dinesh.android.v1.FetchPersonQuery
import com.dinesh.android.v1.FetchPersonsQuery
import com.dinesh.android.v1.SearchPersonsByAgeQuery
import com.dinesh.android.v1.SearchPersonsByNameAgeQuery
import com.dinesh.android.v1.SearchPersonsByNameQuery

fun FetchDataQuery.Data.toFetchData(): Data {
    return Data(
        hello = hello,
        numbers = numbers?.map { it }
    )
}

fun FetchGreetingQuery.Data.toFetchGreeting(): Greeting {
    return Greeting(
        hello = hello
    )
}

fun FetchNumbersQuery.Data.toFetchNumbers(): Numbers {
    return Numbers(
        numbers = numbers?.map { it }
    )
}

fun FetchPersonsQuery.Data.toFetchPersons(): Persons {
    return Persons(
        persons = persons?.map { Person(it?.name, it?.age, Address(it?.address?.street, it?.address?.city, it?.address?.country)) }
    )
}

fun FetchPersonQuery.Person.toFetchPerson(): Person {
    return Person(
        name = this.name,
        age = this.age,
        address = this.toFetchAddress()
//        address = Address(street = this.address?.street, city = this.address?.city, country = this.address?.country)
    )
}

fun FetchPersonQuery.Person.toFetchAddress(): Address {
    return Address(
        street = this.address?.street,
        city = this.address?.city,
        country = this.address?.country,
    )
}

fun SearchPersonsByNameAgeQuery.Data.toFetchPersonsByNameAge(): Persons {
    return Persons(
        persons = this.searchPersons?.map { Person(it?.name, it?.age, Address(null, null, null)) }
//        persons = this.searchPersons?.map { Person(it?.name, it?.age, Address(it?.address?.street, it?.address?.city, it?.address?.country)) }
    )
}

fun SearchPersonsByNameQuery.Data.toFetchPersonsByName(): Persons {
    return Persons(
        persons = this.searchPersons?.map { Person(it?.name, it?.age, Address(null, null, null)) }
    )
}

fun SearchPersonsByAgeQuery.Data.toFetchPersonsByAge(): Persons {
    return Persons(
        persons = this.searchPersons?.map { Person(it?.name, it?.age, Address(null, null, null)) }
    )
}

fun DeletePersonsByNameMutation.Data.toDeletePersonsByName() : Persons {
    return Persons(
        persons = this.deletePerson?.map { Person(it?.name, it?.age, Address(null, null, null)) }
    )
}