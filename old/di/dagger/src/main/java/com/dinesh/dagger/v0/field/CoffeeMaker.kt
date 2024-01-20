package com.dinesh.dagger.v0.field

import android.util.Log
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

private val TAG = "log_Dagger_Field"

class DI {
    fun main() {
        val coffeeComponent = DaggerCoffeeComponent.create()
        val coffeeMaker = CoffeeMaker()
        coffeeComponent.inject(coffeeMaker)
        coffeeMaker.makeCoffee()
    }
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

@Module
class CoffeeModule {
    @Provides
    fun provideHeater(): Heater {
        return Heater()
    }

    @Provides
    fun provideCoffee(): Coffee {
        return Coffee()
    }
}

@Component(modules = [CoffeeModule::class])
interface CoffeeComponent {
    fun inject(coffeeMaker: CoffeeMaker)
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
