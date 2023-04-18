/*
 * @Description: mysql邮箱登录
 * @Author: 唐健峰
 * @Date: 2023-04-15 16:01:54
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 15:28:18
 */
package tjf.emuseum.emuseum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import tjf.emuseum.emuseum.data.myBatis.mapper.SaltMapper;
import tjf.emuseum.emuseum.data.myBatis.mapper.UserMapper;
import tjf.emuseum.emuseum.entity.LoginUser;
import tjf.emuseum.emuseum.entity.User;
import tjf.emuseum.emuseum.utils.MD5PasswordEncoder;

@Service("userDetailsServiceImpl2")
public class UserDetailsServiceImpl2 implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    SaltMapper saltMapper;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        // （认证，即校验该用户是否存在）查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, mail);
        User user = userMapper.selectOne(queryWrapper);
        // 如果没有查询到用户
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("邮箱号或者密码错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("email", (Object) mail);

        // (授权，即查询用户具有哪些权限)查询对应的用户信息
        // 定义一个权限集合
        List<String> list = new ArrayList<String>(List.of("admin"));

        // 把数据封装成UserDetails返回
        return new LoginUser(user, list);
    }

}
