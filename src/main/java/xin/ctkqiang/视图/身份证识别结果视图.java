package xin.ctkqiang.视图;

import xin.ctkqiang.控制器.日志记录器;

public class 身份证识别结果视图 {

    /**
     * 该方法将OCR识别结果显示给用户。
     * 
     * @param 识别结果 从身份证图像中识别出的文本内容。
     */
    public void 显示识别结果(String 识别结果) {
        try {
            if (识别结果 != null) {
                日志记录器.信息("OCR识别结果: " + 识别结果);
            } else {
                日志记录器.警告("未获取到有效的OCR识别结果。");
            }
        } catch (Exception e) {
            日志记录器.错误("在显示OCR识别结果时发生异常: " + e.getMessage());
        }
    }
}