package xin.ctkqiang.视图;

import xin.ctkqiang.控制器.日志记录器;
import xin.ctkqiang.模型.输出模式;

public class 身份证识别结果视图 {

    @SuppressWarnings("unused")
    private 输出模式 当前输出模式 = 输出模式.控制台;
    private boolean 记录日志 = false;

    /**
     * 设置结果输出模式
     * 
     * @param 模式 输出模式
     */
    public void 设置输出模式(输出模式 模式) {
        this.当前输出模式 = 模式;
        日志记录器.信息("已设置输出模式为: " + 模式);
    }

    /**
     * 该方法将OCR识别结果显示给用户。
     * 
     * @param 识别结果 从身份证图像中识别出的文本内容。
     */
    public void 显示识别结果(String 识别结果) {
        try {
            if (识别结果 != null) {

                if (识别结果.contains("身份证信息解析结果")) {
                    显示格式化身份证信息(识别结果);
                } else if (识别结果.matches("\\d{17}[0-9X]")) {
                    /**
                     * 如果是18位身份证号码，直接显示并格式化
                     */
                    显示简洁身份证格式(识别结果);
                } else {
                    日志记录器.信息("OCR识别结果: " + 识别结果);
                }
            } else {
                日志记录器.警告("未获取到有效的OCR识别结果。");
            }
        } catch (Exception e) {
            日志记录器.错误("在显示OCR识别结果时发生异常: " + e.getMessage());
        }
    }

    /**
     * 以简洁格式显示身份证号码信息
     * 
     * @param 身份证号码 18位身份证号码
     * @param 记录日志  是否记录日志信息
     */
    private void 显示简洁身份证格式(String 身份证号码) {
        if (身份证号码 == null || 身份证号码.length() != 18) {
            日志记录器.警告("无效的身份证号码格式");
            return;
        }

        try {
            /**
             * 提取各部分信息
             */
            String 地区代码 = 身份证号码.substring(0, 6);
            String 年 = 身份证号码.substring(6, 10);
            String 月 = 身份证号码.substring(10, 12);
            String 日 = 身份证号码.substring(12, 14);
            String 顺序码 = 身份证号码.substring(14, 17);

            char 性别码 = 身份证号码.charAt(16);
            char 校验码 = 身份证号码.charAt(17);

            /**
             * 判断性别
             */
            String 性别 = (Integer.parseInt(String.valueOf(性别码)) % 2 == 0) ? "女" : "男";

            /**
             * 获取地区信息（这里使用示例地区，实际应从地区代码表中查询）
             */
            String 地区信息 = 获取地区信息(地区代码);

            /**
             * 构建格式化输出
             */
            StringBuilder 结果 = new StringBuilder();
            结果.append(地区信息).append("\n");
            结果.append(年).append(月).append(日).append("\n\n");
            结果.append(年).append("年").append(月).append("月").append(日).append("出生于").append("\n");
            结果.append(顺序码).append("\n\n");
            结果.append("顺序码").append("\n");
            结果.append(性别码).append("\n\n");
            结果.append("顺序码，").append(性别).append("\n");
            结果.append(校验码).append("\n\n");
            结果.append("验证码");

            if (this.记录日志) {
                日志记录器.信息("\n" + 结果.toString());
            }

            return;
        } catch (Exception e) {
            日志记录器.错误("解析身份证号码时发生错误: " + e.getMessage());
        }
    }

    /**
     * 根据地区代码获取地区信息
     * 
     * @param 地区代码 6位地区代码
     * @return 地区信息
     */
    private String 获取地区信息(String 地区代码) {
        switch (地区代码) {
            /**
             * 原有地区代码
             */
            case "441721":
                return "广东省阳江市阳西县";
            case "350125":
                return "福建省福州市永泰县";
            case "130982":
                return "河北省沧州市任丘市";
            case "210504":
                return "辽宁省本溪市市辖区明山区";
            case "231201":
                return "黑龙江省绥化市市辖区";

            /**
             * 江西省地区代码
             */
            case "360000":
                return "江西省";
            case "360100":
                return "江西省南昌市";
            case "360200":
                return "江西省景德镇市";
            case "360300":
                return "江西省萍乡市";
            case "360400":
                return "江西省九江市";
            case "360500":
                return "江西省新余市";
            case "360600":
                return "江西省鹰潭市";
            case "360700":
                return "江西省赣州市";
            case "360800":
                return "江西省吉安市";
            case "360900":
                return "江西省宜春市";
            case "361000":
                return "江西省抚州市";
            case "361100":
                return "江西省上饶市";
            case "361102":
                return "江西省上饶市信州区";
            case "361103":
                return "江西省上饶市广丰区";
            case "361121":
                return "江西省上饶市上饶县";
            case "361123":
                return "江西省上饶市玉山县";
            case "361124":
                return "江西省上饶市铅山县";
            case "361125":
                return "江西省上饶市横峰县";
            case "361126":
                return "江西省上饶市弋阳县";
            case "361127":
                return "江西省上饶市余干县";
            case "361128":
                return "江西省上饶市鄱阳县";
            case "361129":
                return "江西省上饶市万年县";
            case "361130":
                return "江西省上饶市婺源县";
            case "361181":
                return "江西省上饶市德兴市";
            case "362400":
                return "江西省抚州地区";
            case "362424":
                return "江西省抚州地区南丰县";

            /**
             * 其他常见地区代码
             */
            case "110000":
                return "北京市";
            case "310000":
                return "上海市";
            case "440000":
                return "广东省";
            case "440100":
                return "广东省广州市";
            case "440300":
                return "广东省深圳市";
            case "330000":
                return "浙江省";
            case "330100":
                return "浙江省杭州市";
            default:
                return "未知地区(" + 地区代码 + ")";
        }
    }

    /**
     * 以美观的方式显示格式化的身份证信息
     * 
     * @param 身份证信息 格式化的身份证信息字符串
     */
    private void 显示格式化身份证信息(String 身份证信息) {
        String[] 信息行 = 身份证信息.split("\n");

        StringBuilder 美化信息 = new StringBuilder();

        美化信息.append("\n┌───────────────────────────────────────────────┐\n");
        美化信息.append("│            身份证信息识别结果                  │\n");
        美化信息.append("├───────────────────────────────────────────────┤\n");

        for (int i = 1; i < 信息行.length; i++) {
            String 行 = 信息行[i].trim();

            if (!行.isEmpty()) {
                美化信息.append("│ ").append(行);

                int 空格数 = 45 - 行.length();

                for (int j = 0; j < 空格数; j++) {
                    美化信息.append(" ");
                }

                美化信息.append("│\n");
            }
        }

        美化信息.append("└───────────────────────────────────────────────┘\n");

        String 结果 = 美化信息.toString();
        日志记录器.信息(结果);
    }
}