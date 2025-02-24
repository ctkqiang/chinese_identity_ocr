package xin.ctkqiang;

import java.util.Scanner;
import xin.ctkqiang.控制器.日志记录器;
import xin.ctkqiang.控制器.身份证处理控制器;
import xin.ctkqiang.模型.身份证处理模型;
import xin.ctkqiang.视图.身份证识别结果视图;

/**
 * 
 * 版权：Copyright © 2025 @ctkqiang , Inc. All rights reserved.
 * 描述：该类是中华人民共和国居民身份证 OCR 识别与图像处理系统的核心入口类。
 * 它采用单例模式确保系统全局只有一个实例，避免资源的重复初始化和浪费。
 * 系统的主要功能是对身份证图像进行处理和 OCR 识别，并验证识别出的身份证号码的合法性。
 * 其工作流程如下：
 * 1. 初始化阶段：在静态代码块中加载 OpenCV 库和设置 Tesseract OCR 库的相关路径，确保依赖库能正常使用。
 * 2. 实例创建：通过单例模式创建系统实例，初始化模型、视图和控制器对象，实现各组件之间的协作。
 * 3. 图像获取：从命令行参数或用户输入获取身份证图像的路径。
 * 4. 处理与识别：调用控制器的方法对图像进行处理、OCR 识别，并解析识别结果。
 * 5. 结果展示：将处理和识别后的结果通过视图进行展示。
 * 6. 验证：对识别出的身份证号码进行合法性验证，确保结果的准确性。
 * 
 * 该系统在企业级应用场景中具有重要意义，可用于各种需要身份验证的业务流程，如金融开户、酒店入住登记等，
 * 提高身份验证的效率和准确性，减少人工操作带来的误差和风险。
 * 
 * @author 钟智强 johnmelodymel@qq.com
 * @version 1.0
 * @since 2025年2月26日
 */
public class 中华人民共和国居民身份证_OCR_识别与图像处理系统 {
    /**
     * @object 模型: 创建身份证处理模型对象。
     *         该模型对象负责处理身份证图像，包括图像的预处理、特征提取等操作，
     *         为后续的 OCR 识别提供高质量的图像数据。
     */
    private final 身份证处理模型 模型;

    /**
     * @object 视图: 创建身份证识别结果视图对象。
     *         该视图对象用于展示身份证 OCR 识别的结果，将识别信息以友好的方式呈现给用户。
     */
    private final 身份证识别结果视图 视图;

    /**
     * @object 控制器: 创建身份证处理控制器对象，并传入模型和视图对象。
     *         控制器对象起到协调模型和视图的作用，接收用户输入的图像路径，
     *         调用模型进行图像的处理和 OCR 识别，然后将结果传递给视图进行展示，
     *         同时还负责验证识别出的身份证号码的合法性。
     */
    private final 身份证处理控制器 控制器;

    /**
     * 系统的单例实例。
     * 使用单例模式确保系统全局只有一个实例，
     * 避免重复初始化和资源浪费，保证系统的稳定性和一致性。
     */
    private static 中华人民共和国居民身份证_OCR_识别与图像处理系统 实例;

    static {
        try {
            /**
             * 设置Tesseract库路径和数据路径
             * Tesseract 是一个开源的 OCR 引擎，设置其库路径和数据路径，
             * 确保系统能够正确调用该引擎进行身份证号码的识别。
             */
            System.setProperty("jna.library.path", "/opt/homebrew/lib");
            System.setProperty("TESSDATA_PREFIX", "/opt/homebrew/share/tessdata");

            /**
             * 加载 OpenCV 库，OpenCV 是一个强大的计算机视觉库，用于图像的处理和分析
             */
            nu.pattern.OpenCV.loadShared();

            日志记录器.信息("成功加载OpenCV库");
            日志记录器.信息("Tesseract数据路径: " + System.getenv("TESSDATA_PREFIX"));
        } catch (Exception e) {

            /**
             * 若 OpenCV 库加载失败，系统无法正常工作，退出程序并返回错误码 0x1
             */
            日志记录器.错误("OpenCV库加载失败: " + e.getMessage());
            System.exit(0x1);
        }
    }

    /**
     * 私有构造函数，确保只能通过单例方法获取系统实例。
     * 在构造函数中初始化模型、视图和控制器对象，完成系统组件的初始化工作。
     */
    private 中华人民共和国居民身份证_OCR_识别与图像处理系统() {
        this.模型 = new 身份证处理模型();
        this.视图 = new 身份证识别结果视图();
        this.控制器 = new 身份证处理控制器(this.模型, this.视图);
    }

    /**
     * 获取系统的单例实例。
     * 该方法采用同步机制，确保在多线程环境下也能正确获取系统的唯一实例。
     * 
     * @return 系统的唯一实例
     */
    public static synchronized 中华人民共和国居民身份证_OCR_识别与图像处理系统 获取实例() {
        if (实例 == null) {
            实例 = new 中华人民共和国居民身份证_OCR_识别与图像处理系统();
        }
        return 实例;
    }

    /**
     * 系统的主入口方法，程序从这里开始执行。
     * 该方法负责获取身份证图像的路径，调用系统实例的控制器进行图像的处理和识别，
     * 并处理可能出现的异常情况。
     * 
     * @param args 命令行参数，可用于传入身份证图像的路径
     */
    public static void main(String[] args) {
        String 图像路径;

        try {
            日志记录器.信息("中华人民共和国居民身份证_OCR_识别与图像处理系统启动！");

            /**
             * 从命令行参数或用户输入获取图像路径
             * 优先从命令行参数中获取图像路径，若未提供则提示用户输入。
             */
            if (args.length >= 1) {
                图像路径 = args[0];
                日志记录器.信息("使用命令行参数的图像路径: " + 图像路径);
            } else {
                try (Scanner 扫描器 = new Scanner(System.in)) {
                    日志记录器.信息("请输入身份证图像路径: ");
                    图像路径 = 扫描器.nextLine().trim();
                }
            }

            /**
             * 去除图像路径首尾的引号
             */
            图像路径 = 图像路径.replaceAll("^['\"]|['\"]$", "");

            if (图像路径 == null || 图像路径.isEmpty()) {
                日志记录器.错误("未提供有效的图像路径");

                /**
                 * 若未提供有效图像路径，系统无法继续工作，退出程序并返回错误码 0x1
                 */
                System.exit(0x1);
            }

            /**
             * 调用系统实例的控制器进行图像的处理和识别
             */
            获取实例().控制器.处理并显示结果(图像路径);
        } catch (Exception e) {
            /**
             * 若系统运行过程中出现异常，退出程序并返回错误码 0x1
             */
            日志记录器.错误("系统运行出错: " + e.getMessage());
            System.exit(0x1);
        }
    }
}