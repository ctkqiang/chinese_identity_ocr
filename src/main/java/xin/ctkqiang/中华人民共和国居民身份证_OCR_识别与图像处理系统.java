package xin.ctkqiang;

import xin.ctkqiang.控制器.日志记录器;
import xin.ctkqiang.控制器.测试;
import xin.ctkqiang.控制器.身份证处理控制器;
import xin.ctkqiang.数据.常数;
import xin.ctkqiang.模型.身份证处理模型;
import xin.ctkqiang.视图.身份证识别结果视图;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 版权：Copyright © 2025 @ctkqiang
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
 * @class 中华人民共和国居民身份证_OCR_识别与图像处理系统
 * @description 身份证OCR识别与图像处理系统的核心入口类
 * @author 钟智强 johnmelodymel@qq.com
 * @version 1.0
 * @since 2025年
 */
public class 中华人民共和国居民身份证_OCR_识别与图像处理系统 {
    /**
     * @field APP_名称
     * @description 应用程序名称，从常量类中获取统一的名称
     */
    private final String APP_名称 = 常数.APP_名称;

    /**
     * @field APP_版本
     * @description 应用程序版本号
     */
    public static final String APP_版本 = 常数.APP_版本;

    /**
     * 应用程序作者
     * 标识系统开发者信息
     */
    public static final String APP_作者 = 常数.APP_作者;

    /**
     * 主程序窗口。
     * 用于显示系统的主界面，包含打开摄像头和选择图片等功能按钮。
     */
    private JFrame 主窗口;

    /**
     * 摄像头预览面板。
     * 用于实时显示摄像头捕获的画面，方便用户进行身份证拍摄。
     */
    private JPanel 摄像头面板;

    /**
     * OpenCV摄像头捕获对象。
     * 用于控制摄像头的开启、关闭和图像捕获等操作。
     */
    private VideoCapture 摄像头;

    /**
     * 摄像头捕获状态标志。
     * true: 表示摄像头正在捕获图像
     * false: 表示摄像头已停止捕获
     */
    private volatile boolean 是否捕获中;

    /**
     * 捕获图像的保存路径。
     * 用于存储摄像头捕获的身份证图像，文件名格式为"captured_id_时间戳.jpg"。
     */
    private String 捕获的图像路径;

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
     * 摄像头设备索引。
     * 0x0: 表示使用系统默认摄像头（通常是内置摄像头）
     * 0x1: 表示使用第一个外接摄像头
     * 0x2: 表示使用第二个外接摄像头，以此类推
     */
    private static final int 选中摄像头索引 = 0x0;

    /**
     * 系统运行环境标志。
     * true: 表示系统运行在生产环境中，将执行正常的身份证识别流程
     * false: 表示系统运行在测试环境中，将执行预设的测试用例
     */
    private static final boolean 是否为生产环境 = true;

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
     * 初始化系统界面。
     * 创建主窗口并设置其布局、大小和位置，
     * 添加功能按钮并注册相应的事件处理器。
     */
    private void 初始化界面() {
        主窗口 = new JFrame(this.APP_名称);
        主窗口.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        主窗口.setSize(800, 600);

        JPanel 主面板 = new JPanel(new BorderLayout());

        JPanel 信息面板 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel 版本标签 = new JLabel("版本: " + APP_版本);
        JLabel 作者标签 = new JLabel("作者: " + APP_作者);
        信息面板.add(版本标签);
        信息面板.add(作者标签);
        主面板.add(信息面板, BorderLayout.NORTH);

        JPanel 按钮面板 = new JPanel(new FlowLayout());
        JButton 打开摄像头按钮 = new JButton("打开摄像头");
        JButton 选择图片按钮 = new JButton("选择图片");

        按钮面板.add(打开摄像头按钮);
        按钮面板.add(选择图片按钮);

        打开摄像头按钮.addActionListener(e -> 打开摄像头窗口());
        选择图片按钮.addActionListener(e -> 选择图片());

        主面板.add(按钮面板, BorderLayout.SOUTH);
        主窗口.add(主面板);

        主窗口.setLocationRelativeTo(null);
        主窗口.setVisible(true);
    }

    /**
     * 打开摄像头预览窗口。
     * 创建一个新窗口用于显示摄像头实时画面，
     * 添加图像捕获按钮，并在窗口关闭时自动停止摄像头。
     */
    private JFrame 摄像头窗口;

    private void 打开摄像头窗口() {
        摄像头窗口 = new JFrame("摄像头预览");
        摄像头窗口.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.摄像头面板 = new JPanel();
        this.摄像头面板.setPreferredSize(new Dimension(640, 480));

        JButton 捕获按钮 = new JButton("捕获图像");
        捕获按钮.addActionListener(e -> 捕获图像());

        摄像头窗口.setLayout(new BorderLayout());
        摄像头窗口.add(this.摄像头面板, BorderLayout.CENTER);
        摄像头窗口.add(捕获按钮, BorderLayout.SOUTH);

        摄像头窗口.pack();
        摄像头窗口.setLocationRelativeTo(null);
        摄像头窗口.setVisible(true);

        this.启动摄像头();

        摄像头窗口.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                停止摄像头();
            }
        });
    }

    /**
     * 打开文件选择对话框。
     * 允许用户选择本地存储的身份证图片文件，
     * 支持jpg、jpeg、png和bmp格式。
     */
    private void 选择图片() {
        JFileChooser 文件选择器 = new JFileChooser();
        文件选择器.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "图片文件", "jpg", "jpeg", "png", "bmp"));

        if (文件选择器.showOpenDialog(this.主窗口) == JFileChooser.APPROVE_OPTION) {
            String 图像路径 = 文件选择器.getSelectedFile().getAbsolutePath();
            获取实例().控制器.处理并显示结果(图像路径);
        }
    }

    /**
     * 启动摄像头并开始图像捕获。
     * 在新线程中持续读取摄像头画面并显示在预览面板上，
     * 确保界面响应不被阻塞。
     */
    private void 启动摄像头() {
        摄像头 = new VideoCapture(中华人民共和国居民身份证_OCR_识别与图像处理系统.选中摄像头索引);
        是否捕获中 = true;

        /**
         * 摄像头捕获线程
         * 描述：创建一个专用线程用于摄像头实时画面捕获和显示
         * 工作流程：
         * 1. 设置线程最高优先级，确保画面捕获流畅
         * 2. 持续读取摄像头画面并转换为Java图像格式
         * 3. 使用双缓冲技术在界面上绘制图像
         * 4. 通过帧率控制（30FPS）保持稳定的显示效果
         * 
         * @param 帧    OpenCV图像帧对象
         * @param 缓冲图像 Java图像缓冲区
         * @throws InterruptedException 当线程被中断时抛出
         */
        Thread 摄像头线程 = new Thread(() -> {

            Mat 帧 = new Mat();
            BufferedImage 缓冲图像 = null;

            /**
             * 设置摄像头线程为最高优先级
             */
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            while (是否捕获中 && 摄像头.isOpened()) {
                if (摄像头.read(帧)) {
                    缓冲图像 = 转换为缓冲图像(帧);
                    final BufferedImage 显示图像 = 缓冲图像;

                    SwingUtilities.invokeLater(() -> {

                        if (摄像头面板 != null && 显示图像 != null) {

                            Graphics2D g2d = (Graphics2D) 摄像头面板.getGraphics();
                            if (g2d != null) {
                                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                                g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                                        RenderingHints.VALUE_RENDER_QUALITY);

                                g2d.clearRect(0, 0, 摄像头面板.getWidth(), 摄像头面板.getHeight());

                                g2d.drawImage(显示图像, 0, 0, 摄像头面板.getWidth(), 摄像头面板.getHeight(), null);
                                g2d.dispose();
                            }
                        }
                    });

                    try {
                        Thread.sleep(33);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            帧.release();
        }, "摄像头捕获线程");

        摄像头线程.start();
    }

    /**
     * 捕获当前摄像头画面。
     * 将捕获的图像保存为文件，并传递给控制器进行处理，
     * 完成后自动关闭摄像头。
     */
    private void 捕获图像() {
        Mat 帧 = new Mat();

        if (摄像头.read(帧)) {

            File 目录 = new File("temp/captured");

            if (!目录.exists()) {
                目录.mkdirs();
            }

            捕获的图像路径 = "temp/captured/captured_id_" + System.currentTimeMillis() + ".png";

            Imgcodecs.imwrite(捕获的图像路径, 帧);
            this.停止摄像头();

            获取实例().控制器.处理并显示结果(捕获的图像路径);

        }

        帧.release();
    }

    /**
     * 停止摄像头工作。
     * 关闭摄像头设备并释放相关资源，
     * 确保系统资源得到及时释放。
     */
    private void 停止摄像头() {
        是否捕获中 = false;

        if (摄像头 != null && 摄像头.isOpened()) {
            摄像头.release();
        }
    }

    /**
     * 将OpenCV的Mat矩阵转换为Java的BufferedImage。
     * 处理图像数据格式转换，支持灰度图和彩色图，
     * 便于在Swing界面上显示图像。
     * 
     * @param 矩阵 OpenCV的Mat格式图像数据
     * @return Java BufferedImage格式的图像数据
     */
    private BufferedImage 转换为缓冲图像(Mat 矩阵) {
        int 类型 = 矩阵.channels() == 0x1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;

        byte[] 缓冲 = new byte[矩阵.channels() * 矩阵.cols() * 矩阵.rows()];
        矩阵.get(0, 0, 缓冲);

        BufferedImage 图像 = new BufferedImage(矩阵.cols(), 矩阵.rows(), 类型);
        图像.getRaster().setDataElements(0, 0, 矩阵.cols(), 矩阵.rows(), 缓冲);

        return 图像;
    }

    /**
     * 系统的主入口方法，程序从这里开始执行。
     * 该方法负责获取身份证图像的路径，调用系统实例的控制器进行图像的处理和识别，
     * 并处理可能出现的异常情况。
     * 
     * @param 参数 命令行参数，可用于传入身份证图像的路径
     */
    public static void main(String[] 参数) {
        try {
            if (!中华人民共和国居民身份证_OCR_识别与图像处理系统.是否为生产环境) {
                // 测试模式
                String 测试数据来自江西公安 = "362424197611066438";

                日志记录器.信息("\n ▶ [测试1]: 直接显示身份证号码解析");

                @测试
                身份证识别结果视图 测试视图 = new 身份证识别结果视图();
                测试视图.显示识别结果(测试数据来自江西公安);

                日志记录器.信息("\n ▶ [测试2]: 控制器解析身份证信息");
                身份证处理控制器 测试控制器 = new 身份证处理控制器(
                        new 身份证处理模型(),
                        测试视图);

                String 身份证信息 = 测试控制器.解析身份证信息(测试数据来自江西公安);
                测试视图.显示识别结果(身份证信息);

                日志记录器.信息("\n ✓ 测试完成");
            } else {
                /**
                 * 生产模式：启动图形界面
                 */
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.invokeLater(() -> {
                    中华人民共和国居民身份证_OCR_识别与图像处理系统 系统 = 获取实例();
                    系统.初始化界面();
                });
            }
        } catch (Exception e) {
            日志记录器.错误("系统运行出错: " + e.getMessage());
            System.exit(0x1);
        }
    }
}