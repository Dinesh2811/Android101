package com.dinesh.android.local.v1

data class Data(
    val hello: String?,
    val numbers: List<Int?>?,
)

data class Greeting(
    val hello: String?,
)

data class Numbers(
    val numbers: List<Int?>?,
)

data class Persons(
    val persons: List<Person?>?,
)

data class Person(
    val name: String?,
    val age: Int?,
    val address: Address?,
)

data class Address(
    val street: String?,
    val city: String?,
    val country: String?,
)
