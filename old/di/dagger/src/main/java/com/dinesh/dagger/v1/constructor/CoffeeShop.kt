package com.dinesh.dagger.v1.constructor

import android.util.Log
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

private val TAG = "log_CoffeeShop_Constructor"

@Component(modules = [HeaterModule::class])
interface CoffeeComponent {
    // Add methods to provide instances of ElectricCoffeeMachine and GasCoffeeMachine
    fun getElectricCoffeeMachine(): ElectricCoffeeMachine
    fun getGasCoffeeMachine(): GasCoffeeMachine
}

@Module
class HeaterModule {
    @Provides
    fun provideHeater(): Heater {
        return ElectricHeater()
    }
}

// Application class
class DI {
    fun main() {
        // Create Dagger CoffeeComponent
        val coffeeMachineComponent = DaggerCoffeeComponent.create()

        // Inject the dependencies for ElectricCoffeeMachine and GasCoffeeMachine
        val electricCoffeeMachine = coffeeMachineComponent.getElectricCoffeeMachine()
        val gasCoffeeMachine = coffeeMachineComponent.getGasCoffeeMachine()

        // Make a Latte with Milk using the ElectricCoffeeMachine
        val latteIngredients = listOf(Ingredient.MILK)
        electricCoffeeMachine.makeCoffee(CoffeeType.LATTE, latteIngredients)

        // Make an Espresso with Sugar using the GasCoffeeMachine
        val espressoIngredients = listOf(Ingredient.MILK, Ingredient.SUGAR)
        gasCoffeeMachine.makeCoffee(CoffeeType.ESPRESSO, espressoIngredients)
    }
}

interface Heater {
    fun heat()
}

class ElectricHeater @Inject constructor() : Heater {
    override fun heat() {
        Log.i(TAG, "heat: Electric Heater is heating...")
    }
}

class GasHeater @Inject constructor() : Heater {
    override fun heat() {
        Log.i(TAG, "heat: Gas Heater is heating...")
    }
}

interface CoffeeMachine {
    fun makeCoffee(type: CoffeeType, ingredients: List<Ingredient>)
}

class ElectricCoffeeMachine @Inject constructor(private val heater: Heater) : CoffeeMachine {
    override fun makeCoffee(type: CoffeeType, ingredients: List<Ingredient>) {
        val ingredient = ingredients.joinToString(", ")
        Log.e(TAG, "makeCoffee: Making a ${type.name} coffee with $ingredient")

        heater.heat()

        ingredients.forEach {
            Log.d(TAG, "makeCoffee: Adding $it...")
        }

        Log.i(TAG, "makeCoffee: Brewing ${type.name} coffee...")

        Log.d(TAG, "makeCoffee: ${type.name} coffee is ready with $ingredient")
    }
}

class GasCoffeeMachine @Inject constructor(private val heater: Heater) : CoffeeMachine {
    override fun makeCoffee(type: CoffeeType, ingredients: List<Ingredient>) {
        val ingredient = ingredients.joinToString(", ")
        Log.e(TAG, "makeCoffee: Making a ${type.name} coffee with $ingredient")

        heater.heat()

        ingredients.forEach {
            Log.d(TAG, "makeCoffee: Adding $it...")
        }

        Log.i(TAG, "makeCoffee: Brewing ${type.name} coffee...")
        Log.d(TAG, "makeCoffee: ${type.name} coffee is ready with $ingredient")
    }
}

enum class CoffeeType {
    CAPPUCCINO,
    LATTE,
    MOCHA,
    ESPRESSO
}

enum class Ingredient {
    MILK,
    SUGAR
}

