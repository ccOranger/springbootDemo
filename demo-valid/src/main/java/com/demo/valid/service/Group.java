package com.demo.valid.service;

import javax.validation.GroupSequence;

/**
 *  @author: 李臣臣
 *  @Date: 2020/12/17 0017 16:25
 *  @Description: 默认情况下，不同组别的约束验证是无序的，实现有序的验证方式参数校验 分组 3 组序列
 */
@GroupSequence({First.class,Second.class})
public interface Group {
}
