/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 13:03:58
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 10:17:46
 */
package tjf.emuseum.emuseum.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tjf.emuseum.emuseum.data.myBatis.mapper.SaltMapper;
import tjf.emuseum.emuseum.data.myBatis.mapper.UserMapper;
import tjf.emuseum.emuseum.entity.Salt;
import tjf.emuseum.emuseum.entity.User;
import tjf.emuseum.emuseum.service.Interface.RegisterService;
import tjf.emuseum.emuseum.utils.Mail.ShiroMD5;

@Service
public class RegisterServiceImpl1 implements RegisterService {
    @Autowired
    SaltMapper saltMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    ShiroMD5 shiroMD5;
    @Autowired
    Salt salt;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> register(User user) {
        Map<String, String> responseData = new HashMap<>();
        String salt = "95648c19-bf75-41ee-8d5c-68f8f4029ece";
        user.setPassword(shiroMD5.encryptPassword(user.getPassword(), salt, 1024));
        this.salt.setId(user.getId());
        this.salt.setUserSalt(salt);
        this.salt.setEmail(user.getEmail());
        this.salt.setUserName(user.getUserName());
        saltMapper.insert(this.salt);
        userMapper.insert(user);
        responseData.put("register", "successful");
        return ResponseEntity.ok(responseData);
    }

    @Override
    public ResponseEntity<?> removeAccount(User user) {
        // TODO 注销功能
        return null;
    }
}
