/*
 * @Description:
 * @Author: 唐健峰
 * @Date: 2023-04-15 09:25:04
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 14:52:55
 */
package tjf.emuseum.emuseum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import tjf.emuseum.emuseum.service.provider.EmailAuthenticationProvider;
import tjf.emuseum.emuseum.service.provider.UseridAuthenticationProvider;
import tjf.emuseum.emuseum.service.provider.UsernameAuthenticationProvider;

@Configuration
@EnableWebSecurity // security过滤器
@EnableMethodSecurity(prePostEnabled = true) // 开启注解

public class SecurityConfig {
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Qualifier("MD5PasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Qualifier("useridAuthenticationProvider")
    @Autowired
    private UseridAuthenticationProvider useridAuthenticationProvider;
    @Qualifier("emailAuthenticationProvider")
    @Autowired
    private EmailAuthenticationProvider emailAuthenticationProvider;
    @Qualifier("usernameAuthenticationProvider")
    @Autowired
    private UsernameAuthenticationProvider usernameAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                // 前后端分离是无状态的，不需要session了，直接禁用。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 允许所有OPTIONS请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 允许直接访问授权登录接口
                        .requestMatchers(HttpMethod.GET, "/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/sendMail").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/logout").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("admin")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("admin")
                        // 允许 SpringMVC 的默认错误地址匿名访问
                        .requestMatchers("/error").permitAll()
                        // 其他所有接口必须有Authority信息，Authority在登录成功后的UserDetailsImpl对象中默认设置“ROLE_USER”
                        // .requestMatchers("/**").hasAnyAuthority("ROLE_USER")
                        // 允许任意请求被已登录用户访问，不检查Authority
                        .anyRequest().authenticated())
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                // 加我们自定义的过滤器，替代UsernamePasswordAuthenticationFilter
                //以下为添加授权与验证
                .authenticationProvider(emailAuthenticationProvider)
                .authenticationProvider(useridAuthenticationProvider)
                .authenticationProvider(usernameAuthenticationProvider)
                //加上权限认证替换了上面的UsernamePasswordAuthenticationFilter
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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config, AuthenticationManagerBuilder auth) throws Exception {
        //必须写,否则初始化没有添加多余的Provider导致Handler dispatch failed: java.lang.StackOverflowError问题
        auth
                .authenticationProvider(emailAuthenticationProvider)
                .authenticationProvider(useridAuthenticationProvider)
                .authenticationProvider(usernameAuthenticationProvider);
        return config.getAuthenticationManager();
    }
}
