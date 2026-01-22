package com.example.simplecopy

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class CopyWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // 遍历所有放置在桌面的小组件（可能用户放了多个）
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        internal fun updateAppWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
            // 加载布局
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // --- 按钮 1：复制身份证 ---
            val idIntent = Intent(context, CopyActivity::class.java)
            idIntent.putExtra("COPY_CONTENT", "110101199001011234") // 【这里改你的身份证号】
            idIntent.action = "ACTION_COPY_ID" // 区分动作
            
            // 创建 PendingIntent (Android 12+ 需要 FLAG_IMMUTABLE)
            val idPending = PendingIntent.getActivity(
                context, 
                1, 
                idIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            // 绑定到布局里的按钮 ID (btn_copy_id)
            views.setOnClickPendingIntent(R.id.btn_copy_id, idPending)


            // --- 按钮 2：复制手机号 ---
            val phoneIntent = Intent(context, CopyActivity::class.java)
            phoneIntent.putExtra("COPY_CONTENT", "13800138000") // 【这里改你的手机号】
            phoneIntent.action = "ACTION_COPY_PHONE"
            
            val phonePending = PendingIntent.getActivity(
                context, 
                2, 
                phoneIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            // 绑定到布局里的按钮 ID (btn_copy_phone)
            views.setOnClickPendingIntent(R.id.btn_copy_phone, phonePending)

            // 更新微件
            manager.updateAppWidget(widgetId, views)
        }
    }
}
