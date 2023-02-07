package com.yitong.mbank.opencv

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yitong.mbank.opencv.databinding.ActivityMainBinding
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
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
        viewbind.bt.setOnClickListener {
            togray()
            saveImage()
        }
    }

    /**
     * 将图像存本地
     */
    private fun saveImage(){
        val mat = Mat(300,300,CvType.CV_8UC3)
        mat.setTo(Scalar(20.0,127.0,127.0))
        Log.e("limb",cacheDir.absoluteFile.path)
        val fileDir = File(cacheDir.absoluteFile.path)
        if (!fileDir.exists()){
            fileDir.mkdirs()
        }
        val name = System.currentTimeMillis().toString() + "_limb.jpg"
        val tempFile = File(fileDir.absoluteFile.path + File.separator,name)
        Imgcodecs.imwrite(tempFile.absolutePath,mat)

        val bitmap = BitmapFactory.decodeFile(tempFile.path)

        viewbind.iv2.setImageBitmap(bitmap)
    }

    private fun togray() {
        //灰度处理
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.credit_card_guide_2)
        val src = Mat()
        val dst = Mat()
        Utils.bitmapToMat(bitmap, src)
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY)
        Utils.matToBitmap(dst, bitmap)
        viewbind.iv.setImageBitmap(bitmap)
        src.release()
        dst.release()
    }

    /**
     *
     */
    private fun matObject(){
        //从系统中选择一张图象时，可以通过此方式
        val mat = Imgcodecs.imread("")
        // 第一个参数：文件路径。
        // 第二个参数：加载图像的类型。最常见类型：IMREAD_UNCHANGED=-1,不改变加载图像类型，可能包含透明通道。IMREAD_GRAYSCALE=0,加载图像为灰色图像。IMREAD_COLOR=1,加载图像为彩色图像
        val mat2 = Imgcodecs.imread("",Imgcodecs.IMREAD_COLOR)
        // 宽、高、维度、通道数、深度、类型信息
        val width = mat.cols()
        val height = mat2.rows()
        val dims = mat2.dims()
        // 常见通道数目有1、3、4，分别对应单通道、三通道、四通道，其中四通道中通常会有透明通道的数据
        val channels = mat2.channels()
        // 图像深度表示每个通道灰度值所占的大小，图像深度与类型密切相关
        val depth = mat2.depth()
        val type = mat2.type()
    }
}