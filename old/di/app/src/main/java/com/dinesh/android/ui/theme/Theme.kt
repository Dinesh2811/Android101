package com.dinesh.android.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorPalette = lightColorScheme(
    primary = Primary40,
    onPrimary = Color.White,
    primaryContainer = Primary90,
    onPrimaryContainer = Primary10,
    inversePrimary = Primary80,
    secondary = Secondary40,
    onSecondary = Color.White,
    secondaryContainer = Secondary90,
    onSecondaryContainer = Secondary10,
    tertiary = Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = Tertiary90,
    onTertiaryContainer = Tertiary10,
    error = Error40,
    onError = Color.White,
    errorContainer = Error90,
    onErrorContainer = Error10,
    background = Neutral99,
    onBackground = Neutral10,
    surface = NeutralVariant90,
    onSurface = NeutralVariant30,
    inverseSurface = Neutral20,
    inverseOnSurface = Neutral95,
    surfaceVariant = NeutralVariant90,
    onSurfaceVariant = NeutralVariant30,
    outline = NeutralVariant50
)

private val DarkColorPalette = darkColorScheme(
    primary = Primary80,
    onPrimary = Primary20,
    primaryContainer = Primary30,
    onPrimaryContainer = Primary90,
    inversePrimary = Primary40,
    secondary = Secondary80,
    onSecondary = Secondary20,
    secondaryContainer = Secondary30,
    onSecondaryContainer = Secondary90,
    tertiary = Tertiary80,
    onTertiary = Tertiary20,
    tertiaryContainer = Tertiary30,
    onTertiaryContainer = Tertiary90,
    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,
    background = Neutral10,
    onBackground = Neutral90,
    surface = NeutralVariant30,
    onSurface = NeutralVariant80,
    inverseSurface = Neutral90,
    inverseOnSurface = Neutral10,
    surfaceVariant = NeutralVariant30,
    onSurfaceVariant = NeutralVariant80,
    outline = NeutralVariant80
)

@Composable
fun Material3Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        useDynamicColors && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}



/*

//  LightColorPalette.kt


val Primary10 = Color(0xff21005d)
val Primary20 = Color(0xff381e72)
val Primary30 = Color(0xff4f378b)
val Primary40 = Color(0xff6750a4)
val Primary80 = Color(0xffd0bcff)
val Primary90 = Color(0xffeaddff)

val Secondary10 = Color(0xff1d192b)
val Secondary20 = Color(0xff332d41)
val Secondary30 = Color(0xff4a4458)
val Secondary40 = Color(0xff625b71)
val Secondary80 = Color(0xffccc2dc)
val Secondary90 = Color(0xffe8def8)

val Tertiary10 = Color(0xff31111d)
val Tertiary20 = Color(0xff492532)
val Tertiary30 = Color(0xff633b48)
val Tertiary40 = Color(0xff7d5260)
val Tertiary80 = Color(0xffefb8c8)
val Tertiary90 = Color(0xffffd8e4)

val Error10 = Color(0xFF410e0b)
val Error20 = Color(0xFF601410)
val Error30 = Color(0xFF8c1d18)
val Error40 = Color(0xFFb3261e)
val Error80 = Color(0xFFf2b8b5)
val Error90 = Color(0xFFf9dedc)

val Neutral10 = Color(0xFF1c1b1f)
val Neutral20 = Color(0xFF313033)
val Neutral90 = Color(0xFFe6e1e5)
val Neutral95 = Color(0xFFf4eff4)
val Neutral99 = Color(0xFFfffbfe)

val NeutralVariant30 = Color(0xFF49454f)
val NeutralVariant50 = Color(0xFF79747e)
val NeutralVariant60 = Color(0xFF938f99)
val NeutralVariant80 = Color(0xFFcac4d0)
val NeutralVariant90 = Color(0xFFe7e0ec)

val LightColorPalette = lightColorScheme(
    primary = Primary40,
    onPrimary = Color.White,
    primaryContainer = Primary90,
    onPrimaryContainer = Primary10,
    inversePrimary = Primary80,
    secondary = Secondary40,
    onSecondary = Color.White,
    secondaryContainer = Secondary90,
    onSecondaryContainer = Secondary10,
    tertiary = Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = Tertiary90,
    onTertiaryContainer = Tertiary10,
    error = Error40,
    onError = Color.White,
    errorContainer = Error90,
    onErrorContainer = Error10,
    background = Neutral99,
    onBackground = Neutral10,
    surface = NeutralVariant90,
    onSurface = NeutralVariant30,
    inverseSurface = Neutral20,
    inverseOnSurface = Neutral95,
    surfaceVariant = NeutralVariant90,
    onSurfaceVariant = NeutralVariant30,
    outline = NeutralVariant50
)



//  DarkColorPalette.kt

val Primary10 = Color(0xff21005d)
val Primary20 = Color(0xff381e72)
val Primary30 = Color(0xff4f378b)
val Primary40 = Color(0xff6750a4)
val Primary80 = Color(0xffd0bcff)
val Primary90 = Color(0xffeaddff)

val Secondary10 = Color(0xff1d192b)
val Secondary20 = Color(0xff332d41)
val Secondary30 = Color(0xff4a4458)
val Secondary40 = Color(0xff625b71)
val Secondary80 = Color(0xffccc2dc)
val Secondary90 = Color(0xffe8def8)

val Tertiary10 = Color(0xff31111d)
val Tertiary20 = Color(0xff492532)
val Tertiary30 = Color(0xff633b48)
val Tertiary40 = Color(0xff7d5260)
val Tertiary80 = Color(0xffefb8c8)
val Tertiary90 = Color(0xffffd8e4)

val Error10 = Color(0xFF410e0b)
val Error20 = Color(0xFF601410)
val Error30 = Color(0xFF8c1d18)
val Error40 = Color(0xFFb3261e)
val Error80 = Color(0xFFf2b8b5)
val Error90 = Color(0xFFf9dedc)

val Neutral10 = Color(0xFF1c1b1f)
val Neutral20 = Color(0xFF313033)
val Neutral90 = Color(0xFFe6e1e5)
val Neutral95 = Color(0xFFf4eff4)
val Neutral99 = Color(0xFFfffbfe)

val NeutralVariant30 = Color(0xFF49454f)
val NeutralVariant50 = Color(0xFF79747e)
val NeutralVariant60 = Color(0xFF938f99)
val NeutralVariant80 = Color(0xFFcac4d0)
val NeutralVariant90 = Color(0xFFe7e0ec)

val DarkColorPalette = darkColorScheme(
    primary = Primary80,
    onPrimary = Primary20,
    primaryContainer = Primary30,
    onPrimaryContainer = Primary90,
    inversePrimary = Primary40,
    secondary = Secondary80,
    onSecondary = Secondary20,
    secondaryContainer = Secondary30,
    onSecondaryContainer = Secondary90,
    tertiary = Tertiary80,
    onTertiary = Tertiary20,
    tertiaryContainer = Tertiary30,
    onTertiaryContainer = Tertiary90,
    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,
    background = Neutral10,
    onBackground = Neutral90,
    surface = NeutralVariant30,
    onSurface = NeutralVariant80,
    inverseSurface = Neutral90,
    inverseOnSurface = Neutral10,
    surfaceVariant = NeutralVariant30,
    onSurfaceVariant = NeutralVariant80,
    outline = NeutralVariant80
)


 */



/*

val LightColorPalette = lightColors(
    primary = Color(0xff6750a4),
    primaryVariant = Color(0xff381e72),
    onPrimary = Color.White,
    secondary = Color(0xff625b71),
    secondaryVariant = Color(0xff332d41),
    onSecondary = Color.White,
    background = Color(0xFFf4eff4),
    onBackground = Color(0xFF1c1b1f),
    surface = Color(0xFFe7e0ec),
    onSurface = Color(0xFF49454f),
    error = Color(0xffb3261e),
    onError = Color.White,
    isLight = true
)


val DarkColorPalette = darkColors(
    primary = Color(0xffd0bcff),
    primaryVariant = Color(0xff381e72),
    onPrimary = Color(0xff381e72),
    secondary = Color(0xffccc2dc),
    secondaryVariant = Color(0xff332d41),
    onSecondary = Color(0xff332d41),
    background = Color(0xFF1c1b1f),
    onBackground = Color(0xFFe6e1e5),
    surface = Color(0xFF49454f),
    onSurface = Color(0xFFcac4d0),
    error = Color(0xfff2b8b5),
    onError = Color(0xff601410),
    isLight = false
)

 */

/*

val LightPrimary = Color(0xFF6200EE)
val LightPrimaryVariant = Color(0xFF3700B3)
val LightSecondary = Color(0xFF03DAC6)
val LightSecondaryVariant = Color(0xFF018786)
val LightBackground = Color(0xFFF2F2F2)
val LightSurface = Color(0xFFFFFFFF)
val LightError = Color(0xFFB00020)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightOnSecondary = Color(0xFF000000)
val LightOnBackground = Color(0xFF000000)
val LightOnSurface = Color(0xFF000000)
val LightOnError = Color(0xFFFFFFFF)

val LightColorPalette = lightColors(
    primary = LightPrimary,
    primaryVariant = LightPrimaryVariant,
    secondary = LightSecondary,
    secondaryVariant = LightSecondaryVariant,
    background = LightBackground,
    surface = LightSurface,
    error = LightError,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    onError = LightOnError
)


val DarkPrimary = Color(0xff21005d)
val DarkPrimaryVariant = Color(0xff381e72)
val DarkSecondary = Color(0xff625b71)
val DarkSecondaryVariant = Color(0xffccc2dc)
val DarkBackground = Color(0xFF1c1b1f)
val DarkSurface = Color(0xFF49454f)
val DarkError = Color(0xFFb3261e)
val DarkOnPrimary = Color.White
val DarkOnSecondary = Color.White
val DarkOnBackground = Color(0xFFe6e1e5)
val DarkOnSurface = Color(0xFFcac4d0)
val DarkOnError = Color.White

val DarkColorPalette = ColorScheme(
    primary = DarkPrimary,
    primaryVariant = DarkPrimaryVariant,
    secondary = DarkSecondary,
    secondaryVariant = DarkSecondaryVariant,
    background = DarkBackground,
    surface = DarkSurface,
    error = DarkError,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    onError = DarkOnError
)

 */




/*

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer,
    outline = md_theme_light_outline,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    inverseSurface = md_theme_light_inverseSurface,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer,
    outline = md_theme_dark_outline,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    inverseSurface = md_theme_dark_inverseSurface,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

@Composable
fun Material3Theme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

 */





/*
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.darkColors
//import androidx.compose.material.lightColors
private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun Material3Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
 */

