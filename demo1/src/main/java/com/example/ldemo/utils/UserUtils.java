package com.example.ldemo.utils;

import com.example.ldemo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @package:        com.example.ldemo.utils
 * @className:      UserUtils
 * @description:    类作用描述
 * @author:         李臣臣
 * @createDate:     2019/8/20 16:00
 * @updateUser:     李臣臣
 * @updateDate:     2019/8/20 16:00
 * @updateRemark:   The modified content
 * @version:        1.0
 * <p>copyright: Copyright (c) 2019/8/20</p>
 *
 */
public class UserUtils {
    public static User getCurrentHr() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
