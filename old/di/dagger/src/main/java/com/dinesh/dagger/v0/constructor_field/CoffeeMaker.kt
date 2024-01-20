package com.dinesh.dagger.v0.constructor_field

import android.util.Log
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

private val TAG = "log_Dagger_Constructor_Field"

class DI {
    fun main() {
        // Create Dagger component
        val coffeeComponent = DaggerCoffeeComponent.create()

        // Get the CoffeeMaker instance
        val coffeeMaker = coffeeComponent.getCoffeeMaker()

        // Make coffee
        coffeeMaker.makeCoffee()
    }
}

@Module
class CoffeeModule {
    @Provides
    fun provideCoffee(): Coffee {
        return Coffee()
    }

    @Provides
    fun provideHeater(): Heater {
        return Heater()
    }
}

@Component(modules = [CoffeeModule::class])
interface CoffeeComponent {
    fun getCoffeeMaker(): CoffeeMaker
}

class CoffeeMaker @Inject constructor() {
    @Inject
    lateinit var heater: Heater
    @Inject
    lateinit var coffee: Coffee

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
