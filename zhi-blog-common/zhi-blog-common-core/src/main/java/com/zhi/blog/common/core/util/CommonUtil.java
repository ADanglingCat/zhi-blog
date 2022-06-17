package com.zhi.blog.common.core.util;

import com.zhi.blog.common.core.model.Snowflake;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
  * @author Ted
  * @date 2022/5/13
  **/
public class CommonUtil {
    /**
     * 雪花算法
     * @return 唯一id
     */
    public static Long getGlobalId() {
        return Snowflake.nextId();
    }

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
