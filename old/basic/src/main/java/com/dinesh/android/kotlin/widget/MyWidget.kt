package com.dinesh.android.kotlin.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.view.LayoutInflater
import android.widget.RemoteViews
import android.widget.TextView
import com.dinesh.android.R


class MyWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // Iterate through each widget instance
        for (appWidgetId in appWidgetIds) {
            // Create a RemoteViews object and associate it with your custom layout
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Perform any actions or set values on the views
            views.setTextViewText(R.id.widgetTextView, "Hello Widget!")

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}