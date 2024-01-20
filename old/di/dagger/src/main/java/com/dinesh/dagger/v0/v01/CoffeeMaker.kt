package com.dinesh.dagger.v0.v01

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

// Define different types of coffee beans
class ArabicaCoffee @Inject constructor(private val grinder: Grinder, private val roaster: Roaster) {
    fun brew() {
        println("Brewing Arabica coffee...")
        grinder.grind()
        roaster.roast()
    }
}

class RobustaCoffee @Inject constructor(private val grinder: Grinder, private val roaster: Roaster) {
    fun brew() {
        println("Brewing Robusta coffee...")
        grinder.grind()
        roaster.roast()
    }
}

// Define coffee bean processing dependencies
class Grinder @Inject constructor() {
    fun grind() {
        println("Grinding coffee beans...")
    }
}

class Roaster @Inject constructor() {
    fun roast() {
        println("Roasting coffee beans...")
    }
}

// Define a CoffeeMaker that can brew both Arabica and Robusta coffee
class CoffeeMaker @Inject constructor(
    private val arabicaCoffee: ArabicaCoffee,
    private val robustaCoffee: RobustaCoffee
) {
    fun makeCoffee() {
        arabicaCoffee.brew()
        robustaCoffee.brew()
        println("Coffee is ready!")
    }
}

@Component(modules = [CoffeeModule::class])
interface CoffeeComponent {
    fun getCoffeeMaker(): CoffeeMaker
}

@Module
class CoffeeModule {
    @Provides
    fun provideGrinder(): Grinder {
        return Grinder()
    }

    @Provides
    fun provideRoaster(): Roaster {
        return Roaster()
    }

    @Provides
    fun provideArabicaCoffee(grinder: Grinder, roaster: Roaster): ArabicaCoffee {
        return ArabicaCoffee(grinder, roaster)
    }

    @Provides
    fun provideRobustaCoffee(grinder: Grinder, roaster: Roaster): RobustaCoffee {
        return RobustaCoffee(grinder, roaster)
    }
}

class DI{
    fun main() {
        val coffeeComponent = DaggerCoffeeComponent.create()
        val coffeeMaker = coffeeComponent.getCoffeeMaker()
        coffeeMaker.makeCoffee()
    }
}
