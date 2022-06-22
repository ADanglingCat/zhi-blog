package com.zhi.blog.common.service.util;

import com.zhi.blog.common.core.model.ICommonStatus;
import com.zhi.blog.common.service.model.BlogException;

/**
 * @author Ted
 * @date 2022/5/18
 **/
public class AssertUtil {
    public static void assertTrue(boolean flag, ICommonStatus commonStatus) {
        if (!flag) {
            throw new BlogException(commonStatus);
        }
    }

}
