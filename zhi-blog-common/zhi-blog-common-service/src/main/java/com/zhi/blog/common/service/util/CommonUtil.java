package com.zhi.blog.common.service.util;


import com.zhi.blog.common.service.model.Snowflake;

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
