package tjf.emuseum.emuseum.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tjf.emuseum.emuseum.data.myBatis.mapper.UserMapper;
import tjf.emuseum.emuseum.entity.LoginUser;
import tjf.emuseum.emuseum.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/18 17:25
 * @description:
 */
@Service
public class UserDetailsServiceImpl0 implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    // 自定义用户信息在的查询
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // （认证，即校验该用户是否存在）查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userMapper.selectOne(queryWrapper);
        // 如果没有查询到用户
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }

        //  (授权，即查询用户具有哪些权限)查询对应的用户信息
        // 定义一个权限集合
        List<String> list = new ArrayList<String>(Arrays.asList("admin"));

        // 把数据封装成UserDetails返回
        return new LoginUser(user, list);
    }

}

