package com.example.simplecopy

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast

class CopyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 1. 获取要复制的内容
        val textToCopy = intent.getStringExtra("COPY_CONTENT") ?: ""
        
        // 2. 执行复制
        if (textToCopy.isNotEmpty()) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", textToCopy)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "已复制: $textToCopy", Toast.LENGTH_SHORT).show()
        }
        
        // 3. 立即关闭页面，做到“无感”
        finish()
    }
}
