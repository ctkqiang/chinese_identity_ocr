package xin.ctkqiang.控制器;

public class 日志记录器 {
    private static final String 信息表情 = "💡"; // 使用灯泡表情，更直观地表示信息
    private static final String 错误表情 = "🚫"; // 使用禁止符号，更强调错误状态
    private static final String 成功表情 = "✅"; // 保持绿色对勾，表示成功
    private static final String 警告表情 = "⚠️"; // 保持警告符号，标准的警告表示

    public static void 信息(String 消息) {
        System.out.println(信息表情 + " " + 消息);
    }

    public static void 错误(String 消息) {
        System.out.println(错误表情 + " " + 消息);
    }

    public static void 成功(String 消息) {
        System.out.println(成功表情 + " " + 消息);
    }

    public static void 警告(String 消息) {
        System.out.println(警告表情 + " " + 消息);
    }
}