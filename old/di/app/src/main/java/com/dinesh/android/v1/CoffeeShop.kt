package com.dinesh.android.v1

import android.util.Log

private val TAG = "log_CoffeeShop_Manual_DI"

class DI {
    fun main() {
        // Create an ElectricCoffeeMachine with an ElectricHeater
        val electricHeater = ElectricHeater()
        val electricCoffeeMachine = ElectricCoffeeMachine(electricHeater)

        // Make a Latte with Milk using the ElectricCoffeeMachine
        val latteIngredients = listOf(Ingredient.MILK)
        electricCoffeeMachine.makeCoffee(CoffeeType.LATTE, latteIngredients)

        // Create a GasCoffeeMachine with a GasHeater
        val gasHeater = GasHeater()
        val gasCoffeeMachine = GasCoffeeMachine(gasHeater)

        // Make an Espresso with Sugar using the GasCoffeeMachine
        val espressoIngredients = listOf(Ingredient.MILK, Ingredient.SUGAR)
        gasCoffeeMachine.makeCoffee(CoffeeType.ESPRESSO, espressoIngredients)
    }
}

interface Heater {
    fun heat()
}

class ElectricHeater : Heater {
    override fun heat() {
        Log.i(TAG, "heat: Electric Heater is heating...")
    }
}

class GasHeater : Heater {
    override fun heat() {
        Log.i(TAG, "heat: Gas Heater is heating...")
    }
}

interface CoffeeMachine {
    fun makeCoffee(type: CoffeeType, ingredients: List<Ingredient>)
}

class ElectricCoffeeMachine(private val heater: Heater) : CoffeeMachine {
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

class GasCoffeeMachine(private val heater: Heater) : CoffeeMachine {
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
