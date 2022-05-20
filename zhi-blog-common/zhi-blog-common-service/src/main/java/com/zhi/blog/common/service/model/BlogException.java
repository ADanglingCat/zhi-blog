package com.zhi.blog.common.service.model;

import com.zhi.blog.common.core.model.CommonStatus;
import lombok.Getter;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Getter
public class BlogException extends RuntimeException {
    private final CommonStatus commonStatus;

    public BlogException(CommonStatus commonStatus) {
        super(commonStatus.getMsg());
        this.commonStatus = commonStatus;
    }
}
