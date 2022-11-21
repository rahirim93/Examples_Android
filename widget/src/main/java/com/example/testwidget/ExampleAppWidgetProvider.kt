package com.example.testwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class ExampleAppWidgetProvider: AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        if (appWidgetIds != null) {
            for (appWidgetId: Int in appWidgetIds) {
                //var intent = Intent(context, MainActivity::class.java)
                var intent = Intent(context, Receiver::class.java)

                var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

                var views = RemoteViews(context!!.packageName, R.layout.example_widget)
                views.setOnClickPendingIntent(R.id.example_widget_button1, pendingIntent)


                appWidgetManager?.updateAppWidget(appWidgetId, views)
            }
        }
    }
}