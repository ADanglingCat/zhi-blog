package com.zhi.blog.common.service.model;

import com.zhi.blog.common.core.model.ICommonStatus;
import lombok.Getter;

/**
 * @author Ted
 * @date 2022/5/13
 **/
@Getter
public class BlogException extends RuntimeException {
    private final ICommonStatus commonStatus;

    public BlogException(ICommonStatus commonStatus) {
        super(commonStatus.getMsg());
        this.commonStatus = commonStatus;
    }
}
