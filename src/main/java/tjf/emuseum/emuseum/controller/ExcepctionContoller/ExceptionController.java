/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 01:03:58
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 01:04:16
 */
package tjf.emuseum.emuseum.controller.ExcepctionContoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/1/7 13:07
 * @description: 对一些异常返回给前端信息
 */
@CrossOrigin // 支持跨域访问
@ControllerAdvice
@ResponseBody
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
