package com.dinesh.android.clean_code

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val TAG = "log_clean_code_Basic"

/**
 * # Index
 *
 * - [Typealiases][GetUrl]
 * - [Destructuring Declaration][deStructuring]
 *
 */
class Basic


/**
 * # Typealiases
 *
 *  *Don't*
 *
 *     fun imageUrl(): Result<String>
 *     fun videoUrl(): Result<String>
 *
 *  *Do*
 *
 *     fun imageUrl(): Result<ImageUrl>
 *     fun videoUrl(): Result<VideoUrl>
 *
 *   - Use `typealias` to create meaningful type aliases for [ImageUrl] and [VideoUrl].
 *   - Return [Result] of [ImageUrl] for [imageUrl] and [Result] of [VideoUrl] for [videoUrl] instead of `Result<String>`.
 *
 *  [Clean Code Hacks](https://youtu.be/MbIB8J5_zjM?si=aJh5b8Iqpw7FSsNz)
 *
 * @see [Clean Code Hacks](https://youtu.be/MbIB8J5_zjM?si=aJh5b8Iqpw7FSsNz)
 */

interface GetUrl {
    fun imageUrl(): Result<ImageUrl>
    fun videoUrl(): Result<VideoUrl>
}
typealias ImageUrl = String
typealias VideoUrl = String



/**
 * # Destructuring Declaration
 *
 *  *Don't*
 *
 *     val person: Pair<String, Int> = Pair("Dinesh", 25)
 *     Log.i(TAG, "Name: ${person.first}   Age: ${person.second}")
 *
 *  *Do*
 *
 *     val (name,  age) = Pair("Dinesh", 25)
 *     Log.i(TAG, "Name: $name   Age: $age")
 *
 *   - Improve code readability by assigning individual variables like `name` and `age` instead of accessing Pair components.
 *
 *  [Kotlin Destructuring Declarations](https://kotlinlang.org/docs/destructuring-declarations.html)
 *
 * @see [Kotlin Destructuring Declarations](https://kotlinlang.org/docs/destructuring-declarations.html)
 */
fun deStructuring() {
    val (name,  age) = Pair("Dinesh", 25)
    Log.i(TAG, "Name: $name   Age: $age")
}






