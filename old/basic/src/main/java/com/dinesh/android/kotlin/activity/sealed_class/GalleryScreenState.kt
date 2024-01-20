package com.dinesh.android.kotlin.activity.sealed_class

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R


sealed class GalleryScreenState {
    data object Loading : GalleryScreenState()
    data class Loaded(val drawableResourceIDList: List<Int>) : GalleryScreenState()
    data object Error : GalleryScreenState()
}


class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simulate loading the data
        val initialState = GalleryScreenState.Loading
        renderScreenState(initialState)

        // Simulate data loading completed
        val loadedState = GalleryScreenState.Loaded(listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background))
        renderScreenState(loadedState)

        // Simulate an error
        val errorState = GalleryScreenState.Error
        renderScreenState(errorState)
    }

    private fun renderScreenState(state: GalleryScreenState) {
        when (state) {
            is GalleryScreenState.Loading -> {
                // Show loading UI
                // For example: progressBar.visibility = View.VISIBLE
            }
            is GalleryScreenState.Loaded -> {
                // Show loaded UI and bind the loaded data
                // For example:
                // progressBar.visibility = View.GONE
                // imageView.setImageResource(state.drawableResourceIDList[0])
                // You can loop through the list and set images as needed
            }
            is GalleryScreenState.Error -> {
                // Show error UI
                // For example: showErrorDialog()
            }
        }
    }
}
