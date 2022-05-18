package com.zhi.blog.common.core.util;

import com.zhi.blog.common.core.model.Snowflake;

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
}
