package com.example.ldemo.service;

import com.example.ldemo.dao.UserMapper;
import com.example.ldemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @package: com.example.ldemo.service
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/8/20 15:14
 * @updateUser: 李臣臣
 * @updateDate: 2019/8/20 15:14
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userMapper.loadUserByUsername(s);

        if (user == null){
            throw new UsernameNotFoundException("用户名不对");
        }
        return user;
    }
}
