package com.dinesh.dagger.v0.method

import android.util.Log
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

private val TAG = "log_Dagger_Method"

class DI(){
    fun main() {
        // Create the CoffeeMaker
        val coffeeMaker = CoffeeMaker()

        // Make coffee
        coffeeMaker.makeCoffee()
    }
}

@Component(modules = [CoffeeModule::class])
interface CoffeeComponent {
    fun inject(coffeeMaker: CoffeeMaker)
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

class CoffeeMaker {
    @Inject
    lateinit var heater: Heater

    @Inject
    lateinit var coffee: Coffee

    init {
        // Dagger will inject the dependencies here
        DaggerCoffeeComponent.builder()
            .build()
            .inject(this)
    }

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
