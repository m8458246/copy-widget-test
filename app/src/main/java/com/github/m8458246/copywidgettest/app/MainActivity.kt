package com.example.simplecopy

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edit1 = findViewById<EditText>(R.id.edit_content_1)
        val edit2 = findViewById<EditText>(R.id.edit_content_2)
        val btnSave = findViewById<Button>(R.id.btn_save)

        // 1. 读取已有配置 (SharedPreferences)
        val prefs = getSharedPreferences("WidgetConfig", Context.MODE_PRIVATE)
        edit1.setText(prefs.getString("TEXT_1", "默认文本1"))
        edit2.setText(prefs.getString("TEXT_2", "默认文本2"))

        // 2. 点击保存
        btnSave.setOnClickListener {
            val text1 = edit1.text.toString()
            val text2 = edit2.text.toString()

            // 保存数据
            prefs.edit()
                .putString("TEXT_1", text1)
                .putString("TEXT_2", text2)
                .apply()

            // 3. 发送广播通知微件刷新
            // 这是关键一步：不通知的话，微件要等半小时才会自己刷新
            val intent = Intent(this, CopyWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            
            val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(
                ComponentName(application, CopyWidget::class.java)
            )
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            sendBroadcast(intent)

            Toast.makeText(this, "保存成功，桌面微件已更新", Toast.LENGTH_SHORT).show()
            
            // 可选：保存后自动关闭页面
            // finish() 
        }
    }
}
