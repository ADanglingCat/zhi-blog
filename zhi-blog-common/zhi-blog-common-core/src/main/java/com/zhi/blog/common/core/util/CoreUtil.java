package com.zhi.blog.common.core.util;

import com.zhi.blog.common.core.model.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
  * @author Ted
  * @date 2022/5/13
  **/
public class CoreUtil {
    private static final Logger log = LoggerFactory.getLogger("");
    public static void info(String info, Object... params) {
        var exception = new Exception().getStackTrace()[1];
        String className = exception.getClassName();
        String methodName = exception.getMethodName();
        log.info("info {} {} {} {}",className, methodName, info
                , Arrays.stream(params).map(String::valueOf).collect(Collectors.joining(",")));
    }

    public static void warn(String info, Object... params) {
        var exception = new Exception().getStackTrace()[1];
        String className = exception.getClassName();
        String methodName = exception.getMethodName();
        log.warn("info {} {} {} {}",className, methodName, info
                , Arrays.stream(params).map(String::valueOf).collect(Collectors.joining(",")));
    }

    public static void error(String info, Throwable e) {
        log.error("error {}", info, e);
    }

    public static void error(Throwable e) {
        log.error("error ", e);
    }

    /**
     * 雪花算法
     * @return 唯一id
     */
    public static Long getGlobalId() {
        return Snowflake.nextId();
    }

    /**
     * @param obj obj
     * @return 类是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional) {
            return ((Optional<?>) obj).isEmpty();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        // else
        return false;
    }


}
