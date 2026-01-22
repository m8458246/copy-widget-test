package com.example.simplecopy

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class CopyWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        internal fun updateAppWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // 设置按钮 1 (身份证) 的点击事件
            val idIntent = Intent(context, CopyActivity::class.java)
            idIntent.putExtra("COPY_CONTENT", "110101199001011234") // 这里填你要复制的内容
            //以此确保Intent唯一性
            idIntent.action = "ACTION_COPY_ID" 
            val idPending = PendingIntent.getActivity(context, 1, idIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.btn_copy_id, idPending)

            // 设置按钮 2 (手机号) 的点击事件
            val phoneIntent = Intent(context, CopyActivity::class.java)
            phoneIntent.putExtra("COPY_CONTENT", "13800138000") // 这里填你要复制的内容
            phoneIntent.action = "ACTION_COPY_PHONE"
            val phonePending = PendingIntent.getActivity(context, 2, phoneIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.btn_copy_phone, phonePending)

            manager.updateAppWidget(widgetId, views)
        }
    }
}
