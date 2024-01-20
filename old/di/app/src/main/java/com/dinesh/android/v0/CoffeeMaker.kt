package com.dinesh.android.v0

import android.util.Log

private val TAG = "log_Manual_DI"

class DI(){
    fun main() {
        // Create instances of Heater and Coffee
        val heater = Heater()
        val coffee = Coffee()

        // Create the CoffeeMaker and pass the dependencies
        val coffeeMaker = CoffeeMaker(heater, coffee)

        // Make coffee
        coffeeMaker.makeCoffee()
    }
}

class CoffeeMaker(private val heater: Heater, private val coffee: Coffee) {
    fun makeCoffee() {
        heater.heat()
        coffee.brew()
        println("Coffee is ready!")
        Log.e(TAG, "makeCoffee: Coffee is ready!")
    }
}

class Coffee {
    fun brew() {
        println("Brewing coffee...")
        Log.d(TAG, "brew: Brewing coffee...")
    }
}

class Heater {
    fun heat() {
        println("Heating the heater...")
        Log.d(TAG, "heat: Heating the heater...")
    }
}
