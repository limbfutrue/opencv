package com.yitong.mbank.opencv.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.yitong.mbank.opencv.R
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.File

object MatUtils {
    /**
     * 将mat图像转换成bitmap
     * @param filePath 图片路径
     */
    fun matToBitmap(filePath:String) :Bitmap{
        // 获取源图像对象mat
        val src = Imgcodecs.imread(filePath)
        // 获取图像宽高
        val srcWidth = src.width()
        val srcHeight = src.height()
        // 创建bitmap
        val bm = Bitmap.createBitmap(srcWidth,srcHeight,Bitmap.Config.ARGB_8888)
        // 创建目标mat图像
        val dst = Mat()
        // 将图像从一种颜色空间转换为另一种颜色
        Imgproc.cvtColor(src,dst,Imgproc.COLOR_BGR2RGBA)
        // 将mat转换成bitmap
        Utils.matToBitmap(dst,bm)
        return bm
    }

    /**
     * 图片灰度处理
     * @param context
     * @param resId
     * @param imgView
     */
    fun togray(context: Context,resId:Int,imgView:ImageView) {
        //灰度处理
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        val src = Mat()
        val dst = Mat()
        Utils.bitmapToMat(bitmap, src)
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY)
        Utils.matToBitmap(dst, bitmap)
        imgView.setImageBitmap(bitmap)
        src.release()
        dst.release()
    }

    /**
     * 将图像存本地
     */
    fun saveImage(context: Context,path:String){
        val mat = Mat(300,300, CvType.CV_8UC3)
        mat.setTo(Scalar(20.0,127.0,127.0))
        Log.e("limb","path=$path")
        val fileDir = File(path)
        if (!fileDir.exists()){
            fileDir.mkdirs()
        }
        val name = System.currentTimeMillis().toString() + "_limb.jpg"
        val tempFile = File(fileDir.absoluteFile.path + File.separator,name)
        Imgcodecs.imwrite(tempFile.absolutePath,mat)
    }

    /**
     * api介绍
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