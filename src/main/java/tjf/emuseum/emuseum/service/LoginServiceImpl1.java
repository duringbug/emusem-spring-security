/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 10:05:12
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 11:53:09
 */
package tjf.emuseum.emuseum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tjf.emuseum.emuseum.data.redis.RedisCache;
import tjf.emuseum.emuseum.entity.LoginUser;
import tjf.emuseum.emuseum.entity.User;
import tjf.emuseum.emuseum.service.Interface.LoginService;
import tjf.emuseum.emuseum.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/15 10:05
 * @description:
 */
@Service
public class LoginServiceImpl1 implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseEntity<?> login(User user) {

        // 通过UsernamePasswordAuthenticationToken获取用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassword());

        // AuthenticationManager委托机制对authenticationToken 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果认证没有通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证通过，使用user生成jwt jwt存入ResponseResult 返回

        // 如果认证通过，拿到这个当前登录用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        // 获取当前用户的userid
        String userid = loginUser.getUser().getId().toString();

        String jwt = JwtUtil.createJWT(userid);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);

        // 把完整的用户信息存入redis userid为key 用户信息为value
        redisCache.setCacheObject("login:" + userid, loginUser);

        return ResponseEntity.ok(map);
    }

    @Override
    public ResponseEntity<?> logout() {
        // 从SecurityContextHolder中的userid
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        // 根据userid找到redis对应值进行删除
        redisCache.deleteObject("login:" + userid);
        return ResponseEntity.ok("注销成功");
    }
}
