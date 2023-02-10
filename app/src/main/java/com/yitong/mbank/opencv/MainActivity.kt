package com.yitong.mbank.opencv

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import com.yitong.mbank.opencv.databinding.ActivityMainBinding
import com.yitong.mbank.opencv.utils.MatUtils
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var viewbind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbind.root)

        /** 第一次在安卓端引入opencv，配置好后构造一个Mat类型就出现这个坑，原因在于未启动OpenCV，无法使用OpenCV中的类给对象初始化，
         * 一般OpenCV的启动都选择在OnResume方法中完成，也就是说，在OnResume方法之前调用OpenCV中的任意方法都是错误的，
         * 见https://blog.csdn.net/wangshuailpp/article/details/52790489。
         * 这里我们在onCreate（）中加入
         * OpenCVLoader.initDebug();
         * 即可
         */
        OpenCVLoader.initDebug()
        MatUtils.saveImage(this,cacheDir.absoluteFile.path)
        viewbind.bt.setOnClickListener {
            MatUtils.togray(this,R.mipmap.credit_card_guide_2,viewbind.iv)
            viewbind.iv2.setImageBitmap(MatUtils.matToBitmap(cacheDir.path + "/a_limb.jpg"))
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }
}