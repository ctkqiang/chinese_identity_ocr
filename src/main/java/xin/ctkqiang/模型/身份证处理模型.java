package xin.ctkqiang.模型;

import java.io.File;

import org.jetbrains.annotations.NotNull;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import xin.ctkqiang.控制器.日志记录器;

/**
 * 此类别为MVC模式中的模型层，主要负责处理与身份证图像相关的核心业务逻辑，
 * 包括图像的处理和光学字符识别（OCR）操作。
 */
public class 身份证处理模型 {
    /**
     * 该方法使用OpenCV库对输入的身份证图像进行一系列处理操作，以提高图像质量，便于后续的OCR识别。
     * 
     * @param 图像路径 输入的身份证图像文件的存储路径。
     * @return 处理后的图像文件的存储路径，如果图像读取失败则返回null。
     */
    private String 创建临时文件(@NotNull String 文件前缀) {
        String 临时文件夹路径 = System.getProperty("user.dir") + File.separator + "temp";
        File 临时目录 = new File(临时文件夹路径);

        if (!临时目录.exists() && !临时目录.mkdirs()) {
            日志记录器.错误("无法创建临时文件夹：" + 临时文件夹路径);
            return null;
        }

        String 暂时目录 = 临时目录.getAbsolutePath() + File.separator +
                文件前缀 + "_" + System.currentTimeMillis() + ".jpg";

        日志记录器.信息("创建临时文件" + 暂时目录);

        return 暂时目录;
    }

    /**
     * 处理输入的身份证图像并对其进行一系列处理操作，以提高图像质量，便于后续的OCR识别。
     * 
     * @param 图像路径 输入的身份证图像文件的存储路径。
     * @return 处理后的图像文件的存储路径，如果图像读取失败则返回null。
     */
    public String 处理图像(@NotNull String 图像路径) {
        // 利用OpenCV的Imgcodecs.imread方法从指定路径读取图像。
        // 若图像读取失败，将返回一个空的Mat对象。
        Mat 图像 = Imgcodecs.imread(图像路径);

        // 检查读取的图像是否为空，若为空则输出错误信息并返回null。
        if (图像.empty()) {
            日志记录器.警告("无法打开或找到指定图像");
            return null;
        }

        // 将彩色图像转换为灰度图像。
        // 灰度图像只有一个通道，能简化后续的图像处理步骤。
        Mat 灰度图像 = new Mat();
        Imgproc.cvtColor(图像, 灰度图像, Imgproc.COLOR_BGR2GRAY);

        // 对灰度图像应用高斯模糊。
        // 高斯模糊通过平滑图像来减少噪声，参数Size(5, 5)指定了高斯核的大小，0表示标准差。
        Mat 模糊图像 = new Mat();
        Imgproc.GaussianBlur(灰度图像, 模糊图像, new Size(0x5, 0x5), 0x0);

        // 对模糊后的图像应用Otsu阈值处理。
        // Otsu方法会自动确定最佳的阈值，将图像中的前景（文本）和背景分离，增强文本的可见性。
        Mat 二值化图像 = new Mat();
        Imgproc.threshold(模糊图像, 二值化图像, 0x0, 0xFF, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

        String 处理后图像路径 = 创建临时文件("processed");

        if (处理后图像路径 == null) {
            return null;
        }

        // 使用OpenCV的Imgcodecs.imwrite方法将处理后的图像保存到指定路径。
        Imgcodecs.imwrite(处理后图像路径, 二值化图像);

        return 处理后图像路径;
    }
}
