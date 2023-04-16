/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 11:26:24
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 14:11:38
 */
package tjf.emuseum.emuseum.controller.filer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/15 11:26
 * @description: 认证的异常处理类
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // 设置HTTP响应状态码为401 Unauthorized
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 设置HTTP响应的Content-Type头为application/json;charset=UTF-8
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 创建一个响应体对象，包含错误信息
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("status", HttpStatus.UNAUTHORIZED.value());
        errorAttributes.put("error", "Unauthorized");
        errorAttributes.put("message", "Access denied. You must be authenticated to access the requested resource.");
        errorAttributes.put("path", request.getRequestURI());
        // 将响应体对象转换为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String errorJson = objectMapper.writeValueAsString(errorAttributes);
        // 将JSON字符串写入HTTP响应体
        PrintWriter writer = response.getWriter();
        writer.write(errorJson);
        writer.flush();
        writer.close();
    }
}
