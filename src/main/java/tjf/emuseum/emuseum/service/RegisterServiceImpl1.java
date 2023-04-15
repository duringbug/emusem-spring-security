/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 13:03:58
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 14:39:04
 */
package tjf.emuseum.emuseum.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tjf.emuseum.emuseum.entity.User;
import tjf.emuseum.emuseum.service.Interface.RegisterService;

@Service
public class RegisterServiceImpl1 implements RegisterService {

    @Override
    public ResponseEntity<?> register(User user) {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("test", "sweniwnnccq");
        // TODO 注册功能
        return ResponseEntity.ok(responseData);
    }

    @Override
    public ResponseEntity<?> removeAccount(User user) {
        // TODO 注销功能
        return null;
    }

}
