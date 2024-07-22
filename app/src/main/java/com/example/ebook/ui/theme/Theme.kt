package com.example.ebook.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun EBookReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = AppColors.PrimaryColor,
            onPrimary = AppColors.OnPrimaryColor,
            secondary = AppColors.SecondaryColor,
            background = AppColors.BackgroundColor,
            surface = AppColors.SurfaceColor,
            onSurface = AppColors.TextPrimaryColor,
            error = AppColors.ErrorColor
            // Add other colors as needed
        )
    } else {
        lightColorScheme(
            primary = AppColors.PrimaryColor,
            onPrimary = AppColors.OnPrimaryColor,
            secondary = AppColors.SecondaryColor,
            background = AppColors.BackgroundColor,
            surface = AppColors.SurfaceColor,
            onSurface = AppColors.TextPrimaryColor,
            error = AppColors.ErrorColor
            // Add other colors as needed
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}