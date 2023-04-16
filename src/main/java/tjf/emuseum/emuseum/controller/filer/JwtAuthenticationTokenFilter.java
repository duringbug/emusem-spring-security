/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-16 03:23:36
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 14:59:44
 */
package tjf.emuseum.emuseum.controller.filer;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import tjf.emuseum.emuseum.data.redis.RedisCache;
import tjf.emuseum.emuseum.entity.LoginUser;
import tjf.emuseum.emuseum.service.UserDetailsServiceImpl3;
import tjf.emuseum.emuseum.utils.JwtUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/15 10:13
 * @description:
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserDetailsServiceImpl3 userDetailsServiceImpl3;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, ServletException {
        // 获取token
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // 遍历所有 cookie
            for (Cookie cookie : cookies) {
                // 获取名为 "token" 的 cookie
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        if (token == null) {
            token = request.getHeader("token");
        }
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        // 从redis中获取用户信息
        redisCache.setCacheName("login");
        LoginUser loginUser = (LoginUser) redisCache.get(userid);
        if (Objects.isNull(loginUser)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 封装Authentication对象存入SecurityContextHolder
        // 获取权限信息封装到Authentication中
        UserDetails userDetails = userDetailsServiceImpl3.loadUserByUsername(userid);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
