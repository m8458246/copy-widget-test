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
    
    // 接收到广播时也会触发更新
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisAppWidget = android.content.ComponentName(context.packageName, CopyWidget::class.java.name)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    companion object {
        internal fun updateAppWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
            // 1. 读取配置
            val prefs = context.getSharedPreferences("WidgetConfig", Context.MODE_PRIVATE)
            val text1 = prefs.getString("TEXT_1", "点击配置") ?: "无内容"
            val text2 = prefs.getString("TEXT_2", "点击配置") ?: "无内容"

            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // 2. 更新界面显示的文字 (假设你的 layout 里 TextView 的 id 分别是 tv_text_1 和 tv_text_2)
            // *注意*：你需要在 widget_layout.xml 给 TextView 加 id
            views.setTextViewText(R.id.tv_text_1, "信息1: $text1")
            views.setTextViewText(R.id.tv_text_2, "信息2: $text2")

            // 3. 设置按钮 1 点击事件
            val idIntent = Intent(context, CopyActivity::class.java)
            idIntent.putExtra("COPY_CONTENT", text1)
            idIntent.action = "ACTION_COPY_1" // 动作标识
            val idPending = PendingIntent.getActivity(context, 1, idIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.btn_copy_id, idPending)

            // 4. 设置按钮 2 点击事件
            val phoneIntent = Intent(context, CopyActivity::class.java)
            phoneIntent.putExtra("COPY_CONTENT", text2)
            phoneIntent.action = "ACTION_COPY_2"
            val phonePending = PendingIntent.getActivity(context, 2, phoneIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.btn_copy_phone, phonePending)

            manager.updateAppWidget(widgetId, views)
        }
    }
}
