package com.stan.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stan.mygithub.R
/**
 * FileName: StartActivity
 * Author: Stan
 * Date: 2019/7/24 10:35
 * Description: 启动页
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class StartActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Intent(this, StartNavigationActivity:: class.java).run{
            startActivity(this)
        }
    }
}