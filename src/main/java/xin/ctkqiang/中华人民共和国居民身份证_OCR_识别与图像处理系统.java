package xin.ctkqiang;

import java.util.Scanner;

import org.opencv.core.Core;

import xin.ctkqiang.控制器.日志记录器;
import xin.ctkqiang.控制器.身份证处理控制器;
import xin.ctkqiang.模型.身份证处理模型;
import xin.ctkqiang.视图.身份证识别结果视图;

public class 中华人民共和国居民身份证_OCR_识别与图像处理系统 {
    /**
     * @object 模型: 创建身份证处理模型对象。
     */
    private final 身份证处理模型 模型;

    /**
     * @object 视图: 创建身份证识别结果视图对象。
     */
    private final 身份证识别结果视图 视图;

    /**
     * @object 控制器: 创建身份证处理控制器对象，并传入模型和视图对象。
     */
    private final 身份证处理控制器 控制器;

    /**
     * 系统的单例实例。
     * 使用单例模式确保系统全局只有一个实例，
     * 避免重复初始化和资源浪费。
     */
    private static 中华人民共和国居民身份证_OCR_识别与图像处理系统 实例;

    static {
        /**
         * 加载OpenCV的本地库，确保程序能够正常使用OpenCV的功能。
         */
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            日志记录器.信息("成功加载OpenCV库");
        } catch (UnsatisfiedLinkError e) {
            日志记录器.错误("无法加载OpenCV库: " + e.getMessage());
            System.exit(0x1);
        }

    }

    private 中华人民共和国居民身份证_OCR_识别与图像处理系统() {
        this.模型 = new 身份证处理模型();
        this.视图 = new 身份证识别结果视图();
        this.控制器 = new 身份证处理控制器(this.模型, this.视图);
    }

    /**
     * 获取系统的单例实例。
     * 
     * @return 系统的唯一实例
     */
    public static synchronized 中华人民共和国居民身份证_OCR_识别与图像处理系统 获取实例() {
        if (实例 == null) {
            实例 = new 中华人民共和国居民身份证_OCR_识别与图像处理系统();
        }
        return 实例;
    }

    public static void main(String[] args) {
        String 图像路径;

        try {
            日志记录器.信息("中华人民共和国居民身份证_OCR_识别与图像处理系统启动！");

            if (args.length < 1) {
                System.exit(0x1);
            }

            /**
             * 定义输入的身份证图像文件的路径。
             */
            try (Scanner 扫描器 = new Scanner(System.in)) {
                System.out.print("请输入身份证图像路径: ");
                图像路径 = 扫描器.nextLine().trim();
            }

            /**
             * 调用控制器的处理并显示结果方法开始处理图像和显示识别结果。
             */
            获取实例().控制器.处理并显示结果(图像路径);
        } catch (Exception e) {
            日志记录器.错误("系统运行出错: " + e.getMessage());
            System.exit(0x1);
        }
    }
}