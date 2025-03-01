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
        } finally {
            System.exit(0x1);
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
            // 直辖市
            case "110000":
                return "北京市";
            case "120000":
                return "天津市";
            case "310000":
                return "上海市";
            case "500000":
                return "重庆市";

            // 特别行政区
            case "810000":
                return "香港特别行政区";
            case "820000":
                return "澳门特别行政区";
            case "710000":
                return "台湾省";

            // 华北地区
            case "130000":
                return "河北省";
            case "130100":
                return "河北省石家庄市";
            case "130200":
                return "河北省唐山市";
            case "130300":
                return "河北省秦皇岛市";

            // 东北地区
            case "210000":
                return "辽宁省";
            case "220000":
                return "吉林省";
            case "230000":
                return "黑龙江省";

            // 华东地区
            case "320000":
                return "江苏省";
            case "320100":
                return "江苏省南京市";
            case "330000":
                return "浙江省";
            case "330100":
                return "浙江省杭州市";
            case "340000":
                return "安徽省";
            case "350000":
                return "福建省";
            case "360000":
                return "江西省";
            case "370000":
                return "山东省";

            // 华中地区
            case "410000":
                return "河南省";
            case "420000":
                return "湖北省";
            case "430000":
                return "湖南省";

            // 华南地区
            case "440000":
                return "广东省";
            case "450000":
                return "广西壮族自治区";
            case "460000":
                return "海南省";

            // 西南地区
            case "510000":
                return "四川省";
            case "520000":
                return "贵州省";
            case "530000":
                return "云南省";
            case "540000":
                return "西藏自治区";

            // 西北地区
            case "610000":
                return "陕西省";
            case "620000":
                return "甘肃省";
            case "630000":
                return "青海省";
            case "640000":
                return "宁夏回族自治区";
            case "650000":
                return "新疆维吾尔自治区";

            /**
             * 江西省南昌市
             */
            case "360100":
                return "江西省南昌市";
            case "360102":
                return "江西省南昌市东湖区";
            case "360103":
                return "江西省南昌市西湖区";
            case "360104":
                return "江西省南昌市青云谱区";
            case "360111":
                return "江西省南昌市青山湖区";
            case "360112":
                return "江西省南昌市新建区";
            case "360113":
                return "江西省南昌市红谷滩区";
            case "360121":
                return "江西省南昌市南昌县";
            case "360123":
                return "江西省南昌市安义县";
            case "360124":
                return "江西省南昌市进贤县";

            /**
             * 江西省景德镇市
             */
            case "360200":
                return "江西省景德镇市";
            case "360202":
                return "江西省景德镇市昌江区";
            case "360203":
                return "江西省景德镇市珠山区";
            case "360222":
                return "江西省景德镇市浮梁县";
            case "360281":
                return "江西省景德镇市乐平市";

            /**
             * 江西省萍乡市
             */
            case "360300":
                return "江西省萍乡市";
            case "360302":
                return "江西省萍乡市安源区";
            case "360313":
                return "江西省萍乡市湘东区";
            case "360321":
                return "江西省萍乡市莲花县";
            case "360322":
                return "江西省萍乡市上栗县";
            case "360323":
                return "江西省萍乡市芦溪县";

            /**
             * 江西省九江市
             */
            case "360400":
                return "江西省九江市";
            case "360402":
                return "江西省九江市濂溪区";
            case "360403":
                return "江西省九江市浔阳区";
            case "360404":
                return "江西省九江市柴桑区";
            case "360423":
                return "江西省九江市武宁县";
            case "360424":
                return "江西省九江市修水县";
            case "360425":
                return "江西省九江市永修县";
            case "360426":
                return "江西省九江市德安县";
            case "360428":
                return "江西省九江市都昌县";
            case "360429":
                return "江西省九江市湖口县";
            case "360430":
                return "江西省九江市彭泽县";
            case "360481":
                return "江西省九江市瑞昌市";
            case "360482":
                return "江西省九江市共青城市";
            case "360483":
                return "江西省九江市庐山市";

            /**
             * 江西省新余市
             */
            case "360500":
                return "江西省新余市";
            case "360502":
                return "江西省新余市渝水区";
            case "360521":
                return "江西省新余市分宜县";

            /**
             * 江西省鹰潭市
             */
            case "360600":
                return "江西省鹰潭市";
            case "360602":
                return "江西省鹰潭市月湖区";
            case "360603":
                return "江西省鹰潭市余江区";
            case "360681":
                return "江西省鹰潭市贵溪市";

            /**
             * 江西省赣州市
             */
            case "360700":
                return "江西省赣州市";
            case "360702":
                return "江西省赣州市章贡区";
            case "360703":
                return "江西省赣州市南康区";
            case "360704":
                return "江西省赣州市赣县区";
            case "360722":
                return "江西省赣州市信丰县";
            case "360723":
                return "江西省赣州市大余县";
            case "360724":
                return "江西省赣州市上犹县";
            case "360725":
                return "江西省赣州市崇义县";
            case "360726":
                return "江西省赣州市安远县";
            case "360727":
                return "江西省赣州市龙南县";
            case "360728":
                return "江西省赣州市定南县";
            case "360729":
                return "江西省赣州市全南县";
            case "360730":
                return "江西省赣州市宁都县";
            case "360731":
                return "江西省赣州市于都县";
            case "360732":
                return "江西省赣州市兴国县";
            case "360733":
                return "江西省赣州市会昌县";
            case "360734":
                return "江西省赣州市寻乌县";
            case "360735":
                return "江西省赣州市石城县";
            case "360781":
                return "江西省赣州市瑞金市";

            /**
             * 江西省吉安市
             */
            case "360800":
                return "江西省吉安市";
            case "360802":
                return "江西省吉安市吉州区";
            case "360803":
                return "江西省吉安市青原区";
            case "360821":
                return "江西省吉安市吉安市";
            case "360822":
                return "江西省吉安市吉水县";
            case "360823":
                return "江西省吉安市峡江县";
            case "360824":
                return "江西省吉安市新干县";
            case "360825":
                return "江西省吉安市永丰县";
            case "360826":
                return "江西省吉安市泰和县";
            case "360827":
                return "江西省吉安市遂川县";
            case "360828":
                return "江西省吉安市万安县";
            case "360829":
                return "江西省吉安市安福县";
            case "360830":
                return "江西省吉安市永新县";
            case "360881":
                return "江西省吉安市井冈山市";

            /**
             * 江西省宜春市
             */
            case "360900":
                return "江西省宜春市";
            case "360902":
                return "江西省宜春市袁州区";
            case "360921":
                return "江西省宜春市奉新县";
            case "360922":
                return "江西省宜春市万载县";
            case "360923":
                return "江西省宜春市上高县";
            case "360924":
                return "江西省宜春市宜丰县";
            case "360925":
                return "江西省宜春市靖安县";
            case "360926":
                return "江西省宜春市铜鼓县";
            case "360981":
                return "江西省宜春市丰城市";
            case "360982":
                return "江西省宜春市樟树市";
            case "360983":
                return "江西省宜春市高安市";

            /**
             * 江西省抚州市
             */
            case "361000":
                return "江西省抚州市";
            case "361002":
                return "江西省抚州市临川区";
            case "361003":
                return "江西省抚州市东乡区";
            case "361021":
                return "江西省抚州市南城县";
            case "361022":
                return "江西省抚州市黎川县";
            case "361023":
                return "江西省抚州市南丰县";
            case "361024":
                return "江西省抚州市崇仁县";
            case "361025":
                return "江西省抚州市乐安县";
            case "361026":
                return "江西省抚州市宜黄县";
            case "361027":
                return "江西省抚州市金溪县";
            case "361028":
                return "江西省抚州市资溪县";
            case "361030":
                return "江西省抚州市广昌县";

            /**
             * 江西省上饶市
             */
            case "361100":
                return "江西省上饶市";
            case "361102":
                return "江西省上饶市信州区";
            /**
             * 作者小贴士：人家就是广丰区的啦~ (。・ω・。)
             * 
             * 💝 广丰美食指南 💝
             * ➤ 广丰米粉：清晨的巷子里飘着米粉的香气，配上秘制的辣椒酱，
             * 还有那一勺金黄的花生油，简直绝了！(๑´ڡ`๑)
             * ➤ 炒粉：每家店都有独特配方，尤其是老街那家，
             * 配上脆嫩的豆芽和香葱，我可以吃三大碗！(๑♡⌓♡๑)
             * ➤ 三杯鸡：用本地土鸡做的三杯鸡，配上广丰特产的青钱柳，
             * 香气四溢，连骨头都想嚼碎！(*╹▽╹*)
             * ➤ 农家豆腐：现打现做的豆腐，配上自制的辣椒酱，
             * 软嫩鲜美，入口即化！(｡♡‿♡｡)
             * 
             * 🍵 特产推荐 🍵
             * ➤ 广丰大白茶：清香淡雅，回甘持久
             * ➤ 青钱柳：广丰特有的养生茶，可降血糖哦~
             * ➤ 篁岭晒秋：晒制的腊肠和红薯干，让人流口水！
             * 
             * 欢迎来玩哦，我可以当导游带你吃遍广丰！✧*。٩(ˊωˋ*)و✧*。
             */
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

            // 北京市各区
            case "110101":
                return "北京市东城区";
            case "110102":
                return "北京市西城区";
            case "110105":
                return "北京市朝阳区";
            case "110106":
                return "北京市丰台区";
            case "110107":
                return "北京市石景山区";
            case "110108":
                return "北京市海淀区";
            case "110109":
                return "北京市门头沟区";
            case "110111":
                return "北京市房山区";
            case "110112":
                return "北京市通州区";
            case "110113":
                return "北京市顺义区";
            case "110114":
                return "北京市昌平区";
            case "110115":
                return "北京市大兴区";
            case "110116":
                return "北京市怀柔区";
            case "110117":
                return "北京市平谷区";
            case "110118":
                return "北京市密云区";
            case "110119":
                return "北京市延庆区";

            // 上海市各区
            case "310101":
                return "上海市黄浦区";
            case "310104":
                return "上海市徐汇区";
            case "310105":
                return "上海市长宁区";
            case "310106":
                return "上海市静安区";
            case "310107":
                return "上海市普陀区";
            case "310109":
                return "上海市虹口区";
            case "310110":
                return "上海市杨浦区";
            case "310112":
                return "上海市闵行区";
            case "310113":
                return "上海市宝山区";
            case "310114":
                return "上海市嘉定区";
            case "310115":
                return "上海市浦东新区";
            case "310116":
                return "上海市金山区";
            case "310117":
                return "上海市松江区";
            case "310118":
                return "上海市青浦区";
            case "310120":
                return "上海市奉贤区";
            case "310151":
                return "上海市崇明区";

            // 广东省主要城市及区
            case "440100":
                return "广东省广州市";
            case "440103":
                return "广东省广州市荔湾区";
            case "440104":
                return "广东省广州市越秀区";
            case "440105":
                return "广东省广州市海珠区";
            case "440106":
                return "广东省广州市天河区";
            case "440300":
                return "广东省深圳市";
            case "440303":
                return "广东省圳unicipalities";
            case "440304":
                return "广东省南山";
            case "440305":
                return "广东省宝安区";
            case "440306":
                return "广东省龙岗区";
            case "440307":
                return "广东省盐田区";

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

        美化信息.append("\n");
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