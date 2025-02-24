package xin.ctkqiang.控制器;

import org.jetbrains.annotations.NotNull;

import xin.ctkqiang.模型.身份证处理模型;
import xin.ctkqiang.视图.身份证识别结果视图;

public class 身份证处理控制器 {
    private 身份证处理模型 模型;
    private 身份证识别结果视图 视图;

    /**
     * 控制器类的构造函数，用于初始化模型和视图对象。
     * 
     * @param 模型 负责身份证图像的处理和OCR识别的模型对象。
     * @param 视图 负责展示OCR识别结果的视图对象。
     */
    public 身份证处理控制器(@NotNull 身份证处理模型 模型, @NotNull 身份证识别结果视图 视图) {
        this.模型 = 模型;
        this.视图 = 视图;
    }

    /**
     * 该方法处理输入的身份证图像，并将最终的OCR识别结果展示给用户。
     * 
     * @param 图像路径 输入的身份证图像文件的存储路径。
     */
    public void 处理并显示结果(@NotNull String 图像路径) {
        // 调用模型层的处理图像方法对输入图像进行处理。
        String 处理后图像路径 = 模型.处理图像(图像路径);

        if (处理后图像路径 != null) {
            // 若图像处理成功，调用模型层的执行OCR方法进行字符识别。
            String 识别结果 = 模型.执行OCR(处理后图像路径);

            // 调用视图层的显示识别结果方法将识别结果展示给用户。
            视图.显示识别结果(识别结果);
        }
    }
}
