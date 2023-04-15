/*
 * @Description:
 * @Author: 唐健峰
 * @Date: 2023-04-15 09:25:04
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 18:52:42
 */
package tjf.emuseum.emuseum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tjf.emuseum.emuseum.controller.filer.JwtAuthenticationTokenFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity // security过滤器
@EnableMethodSecurity(prePostEnabled = true) // 开启注解

public class SecurityConfig implements WebSecurityConfigurer {
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    // 通过用户名验证
    @Qualifier("userDetailsServiceImpl3")
    @Autowired
    private UserDetailsService userDetailsService3;
    @Qualifier("userDetailsServiceImpl1")
    @Autowired
    private UserDetailsService userDetailsService1;
    @Qualifier("userDetailsServiceImpl2")
    @Autowired
    private UserDetailsService userDetailsService2;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Qualifier("MD5PasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用basic明文验证
                .httpBasic().disable()
                // 前后端分离架构不需要csrf保护
                .csrf().disable()
                // 禁用默认登录页
                .formLogin().disable()
                // 禁用默认登出页
                .logout().disable()
                // // 设置异常的EntryPoint，如果不设置，默认使用Http403ForbiddenEntryPoint
                // .exceptionHandling(exceptions ->
                // exceptions.authenticationEntryPoint(invalidAuthenticationEntryPoint))
                // 前后端分离是无状态的，不需要session了，直接禁用。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 允许所有OPTIONS请求
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager())
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .userDetailsService(userDetailsService3)
                .userDetailsService(userDetailsService1)
                .userDetailsService(userDetailsService2)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors();

        return http.build();

    }

    /**
     * 配置跨源访问(CORS)
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService2);
        return new ProviderManager(Arrays.asList(authProvider));
    }

    @Override
    public void init(SecurityBuilder builder) throws Exception {
    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {
    }
}
