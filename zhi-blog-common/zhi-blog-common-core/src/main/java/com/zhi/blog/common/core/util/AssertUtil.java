package com.zhi.blog.common.core.util;

import com.zhi.blog.common.core.exception.BlogException;
import com.zhi.blog.common.core.model.CommonStatus;

/**
 * @author Ted
 * @date 2022/5/18
 **/
public class AssertUtil {
    public static void assertTrue(boolean flag, CommonStatus commonStatus) {
        if (!flag) {
            throw new BlogException(commonStatus);
        }
    }

}
