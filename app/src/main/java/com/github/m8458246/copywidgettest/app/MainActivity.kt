package com.example.simplecopy

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 这里什么都不用做，我们只需要 Widget 功能
        // 甚至连 setContentView 都不需要，打开就是一个白屏然后可以关掉
    }
}
