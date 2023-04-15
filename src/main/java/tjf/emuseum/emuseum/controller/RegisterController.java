/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 12:53:25
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 12:54:42
 */
package tjf.emuseum.emuseum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tjf.emuseum.emuseum.entity.User;
import tjf.emuseum.emuseum.service.Interface.RegisterService;

@RestController
@CrossOrigin
public class RegisterController {
    @Qualifier("registerServiceImpl1")
    @Autowired
    RegisterService registerService;

    @PostMapping("/user/register")
    public ResponseEntity<?> login(@RequestBody User user) {
        return registerService.register(user);
    }
}
