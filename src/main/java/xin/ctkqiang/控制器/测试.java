package xin.ctkqiang.控制器;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试注解，用于标记测试代码。
 * 该注解可以应用于方法、字段或局部变量。
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE })
public @interface 测试 {
    /**
     * 测试描述
     */
    String 描述() default "";

    /**
     * 测试优先级
     */
    int 优先级() default 0;

    /**
     * 是否忽略此测试
     */
    boolean 忽略() default false;
}