/*
 * @Description: id比较
 * @Author: 唐健峰
 * @Date: 2023-04-15 09:16:02
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 16:18:05
 */
package tjf.emuseum.emuseum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import tjf.emuseum.emuseum.data.myBatis.mapper.MenuMapper;
import tjf.emuseum.emuseum.data.myBatis.mapper.UserMapper;
import tjf.emuseum.emuseum.entity.LoginUser;
import tjf.emuseum.emuseum.entity.User;

@Service("userDetailsServiceImpl3")
public class UserDetailsServiceImpl3 implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    // 自定义用户信息在的查询
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // （认证，即校验该用户是否存在）查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userMapper.selectOne(queryWrapper);
        // 如果没有查询到用户
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }

        // TODO (授权，即查询用户具有哪些权限)查询对应的用户信息
        // 定义一个权限集合
        List<String> list = new ArrayList<String>(Arrays.asList("test", "admin"));

        // 把数据封装成UserDetails返回
        return new LoginUser(user, list);
    }

}