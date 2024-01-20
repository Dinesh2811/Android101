package com.dinesh.dagger.v1.constructor_field

import android.util.Log
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

private val TAG = "log_CoffeeShop_Constructor_Field"

// Define a Dagger component interface.
@Component(modules = [HeaterModule::class])
interface CoffeeComponent {
    fun inject(electricCoffeeMachine: ElectricCoffeeMachine)
    fun inject(gasCoffeeMachine: GasCoffeeMachine)
}

// Create a Dagger module for providing dependencies.
@Module
class HeaterModule {
    @Provides
    fun provideHeater(): Heater {
        return ElectricHeater()
    }
}

class DI {
    fun main() {
        // Create a Dagger component instance.
        val component = DaggerCoffeeComponent.create()

        // Inject dependencies into ElectricCoffeeMachine.
        val electricCoffeeMachine = ElectricCoffeeMachine()
        component.inject(electricCoffeeMachine)

        // Make a Latte with Milk using the ElectricCoffeeMachine.
        val latteIngredients = listOf(Ingredient.MILK)
        electricCoffeeMachine.makeCoffee(CoffeeType.LATTE, latteIngredients)

        // Inject dependencies into GasCoffeeMachine.
        val gasCoffeeMachine = GasCoffeeMachine()
        component.inject(gasCoffeeMachine)

        // Make an Espresso with Sugar using the GasCoffeeMachine.
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

class ElectricCoffeeMachine : CoffeeMachine {
    @Inject
    lateinit var heater: Heater // Dagger will inject the Heater instance

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

class GasCoffeeMachine : CoffeeMachine {
    @Inject
    lateinit var heater: Heater // Dagger will inject the Heater instance

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
